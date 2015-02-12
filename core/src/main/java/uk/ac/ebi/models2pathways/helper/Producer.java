package uk.ac.ebi.models2pathways.helper;

import uk.ac.ebi.models2pathways.database.Models2PathwayDAO;
import uk.ac.ebi.models2pathways.entrypoint.Models2Pathways;
import uk.ac.ebi.models2pathways.helper.SBMLModelFactory;
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
        try{
            for (String sbmlID : SBMLModelFactory.getAllModelIdsByAllTaxonomyIds()) {
                SBMLModel sbmlModel = (SBMLModelFactory.getSBMLModel(sbmlID));
                if (sbmlModel.getSBMLModelAnnotations().isEmpty()) {
                    //There is no point to continue for models without annotations
                    continue;
                }
                try {
                    sbmlModelBlockingQueue.put(sbmlModel);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Models2Pathways.setProducerFinished(true);
        } finally {
            Thread.currentThread().interrupt();
        }
    }
}
