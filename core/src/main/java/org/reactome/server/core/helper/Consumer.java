package org.reactome.server.core.helper;

import org.reactome.server.core.database.DatabaseInsertionHelper;
import org.reactome.server.core.entrypoint.Models2Pathways;
import org.reactome.server.core.model.reactome.AnalysisResult;
import org.reactome.server.core.model.sbml.SBMLModel;
import org.reactome.server.core.model.reactome.PathwaySummary;

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

    public Consumer(BlockingQueue<SBMLModel> sbmlModelBlockingQueue, String significantPValue, String extendedPValue) {
        this.sbmlModelBlockingQueue = sbmlModelBlockingQueue;
        this.significantPValue = Double.valueOf(significantPValue);
        this.extendedPValue = Double.valueOf(extendedPValue);
    }

    @Override
    public void run() {
        while (!Models2Pathways.isProducerFinished()) {
            SBMLModel sbmlModel = null;
            try {
                sbmlModel = sbmlModelBlockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean hasMinPValue = true;
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
                System.out.println("Couldn't find pathways on pValue " + significantPValue);
                System.out.println("Request on extended pValue " + extendedPValue);
                hasMinPValue = false;
                analysisResult = AnalysisServiceHandler.getReactomeAnalysisResultBySBMLModel(sbmlModel, extendedPValue);
                if (analysisResult == null) {
                    int tries = 2;
                    while (analysisResult == null && tries != 0) {
                        analysisResult = AnalysisServiceHandler.getReactomeAnalysisResultBySBMLModel(sbmlModel, extendedPValue);
                        tries = tries - 1;
                        if (analysisResult == null && tries == 0) {
                            System.out.println(" >> Couldn't request analysis result");
                        }
                    }
                }
                if (analysisResult == null || analysisResult.getPathwaysFound() == 0) {
                    System.out.println(" >> No pathways found with pValue " + extendedPValue);
                }
            }
            if (analysisResult != null) {
                DatabaseInsertionHelper.createNewBioModelEntry(sbmlModel);
                for (PathwaySummary pathwaySummary : analysisResult.getPathways()) {
                    DatabaseInsertionHelper.createNewPathwayEntry(pathwaySummary);
                    System.out.println(pathwaySummary.getEntities().getpValue());
                    DatabaseInsertionHelper.createNewXReferenceEntry(pathwaySummary, sbmlModel, hasMinPValue, false);
                    System.out.println(" >> " + analysisResult.getPathwaysFound() + " pathways found");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Process finished");
    }
}