package uk.ac.ebi.models2pathways.entrypoint;

import uk.ac.ebi.models2pathways.helper.AnalysisServiceHandler;
import uk.ac.ebi.models2pathways.helper.DatabaseInsertionHelper;
import uk.ac.ebi.models2pathways.model.reactome.AnalysisResult;
import uk.ac.ebi.models2pathways.model.reactome.PathwaySummary;
import uk.ac.ebi.models2pathways.model.sbml.SBMLModel;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class Consumer implements Runnable {
    private final static Double significantPValue = 0.05;
    private final static Double extendedPValue = 0.1;

    private BlockingQueue<SBMLModel> sbmlModelBlockingQueue;

    public Consumer(BlockingQueue<SBMLModel> sbmlModelBlockingQueue) {
        this.sbmlModelBlockingQueue = sbmlModelBlockingQueue;
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
            System.out.println("Consume " + sbmlModel.getBioModelsID());
            boolean hasMinPValue = true;
            AnalysisResult analysisResult = AnalysisServiceHandler.getReactomeAnalysisResultBySBMLModel(sbmlModel, significantPValue);
            if (analysisResult == null) {
                int tries = 2;
                while (analysisResult == null && tries != 0) {
                    analysisResult = AnalysisServiceHandler.getReactomeAnalysisResultBySBMLModel(sbmlModel, significantPValue);
                    tries = tries - 1;
                    if (analysisResult == null && tries == 0) {
                        System.out.println(" >> Couldn't request analysis result");
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
            }
            System.out.println("Process finished");
        }
    }
}