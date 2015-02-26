package org.reactome.server.core.helper;

import org.reactome.server.core.model.sbml.SBMLModel;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class Producer implements Runnable {
    final static Logger logger = Logger.getLogger(Producer.class.getName());

    private BlockingQueue<SBMLModel> sbmlModelBlockingQueue;

    public Producer(BlockingQueue<SBMLModel> sbmlModelBlockingQueue) {
        this.sbmlModelBlockingQueue = sbmlModelBlockingQueue;
    }

    @Override
    public void run() {
        try {
            for (String sbmlID : SBMLModelFactory.getAllModelIdsByAllTaxonomyIds()) {
                SBMLModel sbmlModel = (SBMLModelFactory.getSBMLModelByModelId(sbmlID));
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
        } finally {
            Thread.currentThread().interrupt();
            logger.info("Producer has finished");
        }
    }
}
