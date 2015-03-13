package org.reactome.server.core.helper;

import org.reactome.server.core.entrypoint.Models2Pathways;
import org.reactome.server.core.model.reactome.AnalysisResult;
import org.reactome.server.core.model.reactome.PathwaySummary;
import org.reactome.server.core.model.sbml.SBMLModel;
import org.reactome.server.core.output.FileExporter;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class Consumer implements Runnable {
    final static Logger logger = Logger.getLogger(Consumer.class.getName());

    private Double significantFDR;
    private Double extendedFDR;
    private Double reactionCoverage;
    private BlockingQueue<SBMLModel> sbmlModelBlockingQueue;

    public Consumer(BlockingQueue<SBMLModel> sbmlModelBlockingQueue, String significantFDR, String extendedFDR, String reactionCoverage) {
        this.sbmlModelBlockingQueue = sbmlModelBlockingQueue;
        this.significantFDR = Double.valueOf(significantFDR);
        this.extendedFDR = Double.valueOf(extendedFDR);
        this.reactionCoverage = Double.valueOf(reactionCoverage);
    }

    @Override
    public void run() {
        while (Models2Pathways.isProducerAlive()) {
            SBMLModel sbmlModel = null;
            System.out.println(sbmlModelBlockingQueue.size());
            try {
                sbmlModel = sbmlModelBlockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Remove all trivial chemicals
            if (sbmlModel != null) {
                sbmlModel.getSBMLModelAnnotations().removeAll(new TrivialChemicals().getTrivialChemicals());
            }

            AnalysisResult analysisResult = AnalysisServiceHandler.getReactomeAnalysisResultBySBMLModel(sbmlModel, significantFDR, reactionCoverage);
            if (analysisResult == null) {
                int tries = 2;
                while (analysisResult == null && tries != 0) {
                    analysisResult = AnalysisServiceHandler.getReactomeAnalysisResultBySBMLModel(sbmlModel, significantFDR, reactionCoverage);
                    tries = tries - 1;
                    if (analysisResult == null && tries == 0) {
                        logger.info(sbmlModel + " Couldn't request analysis result");
                        logger.info(sbmlModel + " retry to get analysis result");
                    }
                }
            } else if (analysisResult.getPathways().size() != 0 && analysisResult.getReliablePathways().size() == 0) {
                logger.info("Couldn't find pathways on FDR " + significantFDR);
                logger.info("Request on extended FDR " + extendedFDR);
                analysisResult = AnalysisServiceHandler.getReactomeAnalysisResultBySBMLModel(sbmlModel, extendedFDR, reactionCoverage);
                if (analysisResult == null) {
                    int tries = 2;
                    while (analysisResult == null && tries != 0) {
                        analysisResult = AnalysisServiceHandler.getReactomeAnalysisResultBySBMLModel(sbmlModel, extendedFDR, reactionCoverage);
                        tries = tries - 1;
                        if (analysisResult == null && tries == 0) {
                            logger.info(" >> Couldn't request analysis result");
                        }
                    }
                }
                if (analysisResult == null || analysisResult.getPathwaysFound() == 0) {
                    if (sbmlModel != null) {
                        logger.info("No pathways found with FDR " + extendedFDR + " on " + sbmlModel.getBioModelsID());
                    }
                }
            }
            if (analysisResult != null) {
                    for (PathwaySummary pathwaySummary : analysisResult.getReliablePathways()) {
                        FileExporter.addRow(pathwaySummary, sbmlModel);
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        logger.info("Consumer has finished.");
        FileExporter.closeFile();
        logger.info("\nProcess finished");
    }
}