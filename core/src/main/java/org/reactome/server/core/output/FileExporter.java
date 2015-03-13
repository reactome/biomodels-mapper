package org.reactome.server.core.output;

import org.reactome.server.core.model.reactome.PathwaySummary;
import org.reactome.server.core.model.sbml.SBMLModel;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class FileExporter {
    public static final String TAB = "\t";
    public static final String NEW_LINE = System.getProperty("line.separator");
    final static Logger logger = Logger.getLogger(FileExporter.class.getName());
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM-dd_hh-mm-ss");
    private static final String FILE_NAME = "models2pathways";
    private static String locationPath;
    private static FileWriter fileWriter;

    public static boolean createFile() {
        String locationPath = getLocationPath();
        try {
            fileWriter = new FileWriter(locationPath + ".tsv", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("A tsv file has been created: " + locationPath);
        return true;
    }

    public static void addHeader() {
        try {
            fileWriter.write("DB_ID" + TAB +
                    "ST_ID" + TAB +
                    "BM_ID" + TAB +
                    "BM_NAME" + NEW_LINE);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addRow(PathwaySummary pathwaySummary, SBMLModel sbmlModel) {
        try {
            fileWriter.write(pathwaySummary.getDbId() + TAB +
                    pathwaySummary.getStId() + TAB +
                    sbmlModel.getBioModelsID() + TAB +
                    sbmlModel.getName() + NEW_LINE);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeFile() {
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getLocationPath() {
        return locationPath + FILE_NAME + DATE_FORMAT.format(new Date());
    }

    public static void setLocationPath(String locationPath) {
        FileExporter.locationPath = locationPath;
    }
}
