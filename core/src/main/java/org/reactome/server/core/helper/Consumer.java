package org.reactome.server.core.helper;

import org.reactome.server.core.database.DatabaseInsertionHelper;
import org.reactome.server.core.entrypoint.Models2Pathways;
import org.reactome.server.core.model.reactome.AnalysisResult;
import org.reactome.server.core.model.reactome.PathwaySummary;
import org.reactome.server.core.model.sbml.SBMLModel;
import org.reactome.server.core.output.FileHandler;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class Consumer implements Runnable {
    final static Logger logger = Logger.getLogger(Consumer.class.getName());

    private Double significantPValue;
    private Double extendedPValue;
    private BlockingQueue<SBMLModel> sbmlModelBlockingQueue;
    private Integer numberOfCreatePathwayEntries = 0;
    private Integer numberOfCreateBioModelEntries = 0;
    private Integer numberOfCreateXReferences = 0;

    public Consumer(BlockingQueue<SBMLModel> sbmlModelBlockingQueue, String significantPValue, String extendedPValue) {
        this.sbmlModelBlockingQueue = sbmlModelBlockingQueue;
        this.significantPValue = Double.valueOf(significantPValue);
        this.extendedPValue = Double.valueOf(extendedPValue);
    }

    @Override
    public void run() {
        while (Models2Pathways.isProducerAlive()) {
            SBMLModel sbmlModel = null;
            try {
                sbmlModel = sbmlModelBlockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean hasMinPValue = true;


            //Remove all trivial chemicals
            if (sbmlModel != null) {
                sbmlModel.getSBMLModelAnnotations().removeAll(new TrivialChemicals().getTrivialChemicals());
            }

            AnalysisResult analysisResult = AnalysisServiceHandler.getReactomeAnalysisResultBySBMLModel(sbmlModel, significantPValue);
            if (analysisResult == null) {
                int tries = 2;
                while (analysisResult == null && tries != 0) {
                    analysisResult = AnalysisServiceHandler.getReactomeAnalysisResultBySBMLModel(sbmlModel, significantPValue);
                    tries = tries - 1;
                    if (analysisResult == null && tries == 0) {
                        logger.info(sbmlModel + " Couldn't request analysis result");
                        logger.info(sbmlModel + " retry to get analysis result");
                    }
                }
            } else if (analysisResult.getPathwaysFound() == 0) {
                logger.info("Couldn't find pathways on pValue " + significantPValue);
                logger.info("Request on extended pValue " + extendedPValue);
                hasMinPValue = false;
                analysisResult = AnalysisServiceHandler.getReactomeAnalysisResultBySBMLModel(sbmlModel, extendedPValue);
                if (analysisResult == null) {
                    int tries = 2;
                    while (analysisResult == null && tries != 0) {
                        analysisResult = AnalysisServiceHandler.getReactomeAnalysisResultBySBMLModel(sbmlModel, extendedPValue);
                        tries = tries - 1;
                        if (analysisResult == null && tries == 0) {
                            logger.info(" >> Couldn't request analysis result");
                        }
                    }
                }
                if (analysisResult == null || analysisResult.getPathwaysFound() == 0) {
                    logger.info("No pathways found with pValue " + extendedPValue + " on " + sbmlModel.getBioModelsID());
                }
            }
            if (analysisResult != null) {
                //Database
                DatabaseInsertionHelper.createNewBioModelEntry(sbmlModel);
                numberOfCreateBioModelEntries += 1;
                for (PathwaySummary pathwaySummary : analysisResult.getPathways()) {
                    //Database
                    DatabaseInsertionHelper.createNewPathwayEntry(pathwaySummary);
                    numberOfCreatePathwayEntries += 1;
                    DatabaseInsertionHelper.createNewXReferenceEntry(pathwaySummary, sbmlModel, hasMinPValue, false);
                    numberOfCreateXReferences += 1;
                    //File output
                    if (FileHandler.isInitialized) {
                        FileHandler.addRow(pathwaySummary, analysisResult, sbmlModel, hasMinPValue);
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        logger.info("Consumer has finished.");
        FileHandler.closeFile();

        logger.info("Created pathway entries: " + numberOfCreatePathwayEntries);
        logger.info("Created biomodel entries: " + numberOfCreateBioModelEntries);
        logger.info("Created xReferences: " + numberOfCreateXReferences);
        logger.info("\nProcess finished");
    }
}