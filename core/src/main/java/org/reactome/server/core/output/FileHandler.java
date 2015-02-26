package org.reactome.server.core.output;

import org.reactome.server.core.model.reactome.AnalysisResult;
import org.reactome.server.core.model.reactome.PathwaySummary;
import org.reactome.server.core.model.sbml.SBMLModel;
import org.reactome.server.core.utils.URLBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class FileHandler {
    final static Logger logger = Logger.getLogger(FileHandler.class.getName());

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM-dd_hh-mm-ss");
    private static final String FILE_NAME = "models2pathways";
    public static boolean isInitialized = false;
    private static String locationPath;
    private static FileWriter fileWriter;

    public static void createFile() {
        String locationPath = getLocationPath();
        try {
            fileWriter = new FileWriter(locationPath + ".tsv", true);
            isInitialized = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("A tsv file has been created: " + locationPath);
    }

    public static void addHeader() {
        try {
            fileWriter.write("Species\t" +
                    "BioModel\t" +
                    "Pathway\t" +
                    "Analysis Link\t" +
                    "Total entities (BioModel)\t" +
                    "Total entities (Pathway)\t" +
                    "Matched BioModel entities in Pathway\t" +
                    "Overlap in %\t" +
                    "pValue\t" +
                    "FDR\t" +
                    "isValid\t" +
                    "Reactome Curator\t" +
                    "BioModels Curator\t" +
                    "Comments\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addRow(PathwaySummary pathwaySummary, AnalysisResult analysisResult, SBMLModel sbmlModel, boolean hasMinPValue) {
        try {
            //TODO urls to properties
            fileWriter.write(pathwaySummary.getSpecies().getName() + "\t" +
                    "=HYPERLINK(\"www.ebi.ac.uk/biomodels-main/" + sbmlModel.getBioModelsID() + "\", \"" + sbmlModel.getName() + "\")" + "\t" +
                    "=HYPERLINK(\"www.reactome.org/content/detail/" + pathwaySummary.getStId() + "\", \"" + pathwaySummary.getName() + "\")" + "\t" +
                    "=HYPERLINK(\"" + URLBuilder.getAnalysisURL(pathwaySummary.getSpecies().getDbId(), analysisResult.getToken()) + "\", \" Analysis Link\")" + "\t" +
                    sbmlModel.getSBMLModelAnnotations().size() + "\t" +
                    pathwaySummary.getEntities().getTotal() + "\t" +
                    pathwaySummary.getEntities().getFound() + "\t" +
                    (double) pathwaySummary.getEntities().getFound() / Math.max((double) sbmlModel.getSBMLModelAnnotations().size(), (double) pathwaySummary.getEntities().getTotal()) + "\t" +
                    pathwaySummary.getEntities().getpValue() + "\t" +
                    pathwaySummary.getEntities().getFdr() + "\t" +
                    hasMinPValue + "\n");
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
        FileHandler.locationPath = locationPath;
    }
}
