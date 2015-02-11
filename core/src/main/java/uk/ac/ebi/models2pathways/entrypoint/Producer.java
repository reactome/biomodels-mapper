package uk.ac.ebi.models2pathways.entrypoint;

import uk.ac.ebi.models2pathways.helper.AnalysisServiceHandler;
import uk.ac.ebi.models2pathways.helper.DatabaseInsertionHelper;
import uk.ac.ebi.models2pathways.helper.SBMLModelFactory;
import uk.ac.ebi.models2pathways.model.reactome.AnalysisResult;
import uk.ac.ebi.models2pathways.model.reactome.PathwaySummary;
import uk.ac.ebi.models2pathways.model.sbml.SBMLModel;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class Producer implements Runnable {

    private BlockingQueue<SBMLModel> sbmlModelBlockingQueue;

    public Producer(BlockingQueue<SBMLModel> sbmlModelBlockingQueue) {
        this.sbmlModelBlockingQueue = sbmlModelBlockingQueue;
    }

    @Override
    public void run() {
        for (String sbmlID : SBMLModelFactory.getAllModelIdsByAllTaxonomyIds()) {
            System.out.println("Produce: " + sbmlID);
            SBMLModel sbmlModel = (SBMLModelFactory.getSBMLModel(sbmlID));
            if (sbmlModel.getSBMLModelAnnotations().isEmpty()) {
                //There is no point to continue for models without annotations
                System.out.println(" >> No Annotations found");
                continue;
            }
            try {
                sbmlModelBlockingQueue.put(sbmlModel);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
