package org.reactome.server.models2pathways.biomodels.fetcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.reactome.server.models2pathways.biomodels.fetcher.model.BioModelSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.net.http.HttpClient.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

public class BioModelFetcher {
    private static final Logger logger = LoggerFactory.getLogger("m2pLogger");
    public final static String BIOMODELS_URL = "https://www.ebi.ac.uk/biomodels";

    private final static HttpClient client = newBuilder()
            .version(Version.HTTP_2)
            .followRedirects(Redirect.ALWAYS)
            .build();

    public BioModelFetcher(File destDir) {
        if (!destDir.exists() && destDir.mkdir()) logger.info("{} created", destDir.getAbsolutePath());
        this.destDir = destDir;
    }

    public static String encode(Map<Object, Object> data) {
        var builder = new StringBuilder();
        builder.append('?');
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), UTF_8));
        }
        return builder.toString();
    }

    private final File destDir;

    private final List<String> identifiers = new ArrayList<>();
    private int total = 0;

    public void fetch() {
        collectCuratedModelIds();
        downloadSBMLs();
    }

    private void collectCuratedModelIds() {
        boolean hasNext = true;
        int offset = 0;
        while (hasNext) {
            try {
                Map<Object, Object> params = Map.of(
                        "format", "json",
                        "offset", offset,
                        "numResults", "100",
                        "sort", "id-asc",
                        "domain", "biomodels",
                        "query", "*:* AND curationstatus:\"Manually curated\" AND modelformat:\"SBML\""
                );
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(BIOMODELS_URL + "/search" + encode(params)))
                        .timeout(Duration.ofMinutes(2))
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                BioModelSearch result = new ObjectMapper().readValue(response.body(), BioModelSearch.class);
                result.getModels().forEach(modelSummary -> identifiers.add(modelSummary.getId()));
                total = result.getMatches();
                logger.info("{} / {} model ids fetched", Math.min(offset + 100, total), total);
                hasNext = (offset += 100) < total;
            } catch (Exception e) {
                e.printStackTrace();
                hasNext = false;
            }
        }
    }

    private void downloadSBMLs() {
        for (int i = 0; i < identifiers.size(); i += 100) {
            int upperLimit = Math.min(i + 100, identifiers.size());
            Map<Object, Object> params = Map.of(
                    "models", String.join(",", identifiers.subList(i, upperLimit))
            );
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BIOMODELS_URL + "/search/download" + encode(params)))
                    .header("content-type", "application/x-www-form-urlencoded")
                    .header("accept", "application/zip")
                    .build();
            try {
                HttpResponse<Path> response = client.send(request, HttpResponse.BodyHandlers.ofFileDownload(Path.of("."), CREATE, WRITE));
                File zip = response.body().toFile();
                extract(zip);
                logger.info("{} / {} sbml downloaded", upperLimit, total);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void extract(File zip) throws IOException {
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zip));
        zip.deleteOnExit();
        byte[] buffer = new byte[1024];
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(destDir, zipEntry);
            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                // fix for Windows-created archives
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                // write file content
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
}
