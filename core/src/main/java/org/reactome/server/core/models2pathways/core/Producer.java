package org.reactome.server.core.models2pathways.core;

import org.reactome.server.core.models2pathways.biomodels.helper.SBMLModelFactory;
import org.reactome.server.core.models2pathways.biomodels.model.SBMLModel;

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
            for (Long bioMdId : SBMLModelFactory.getAllBioMdIdsByAllTaxonomyIds()) {
                SBMLModel sbmlModel = (SBMLModelFactory.getSBMLModelByModelId(bioMdId));
                if (sbmlModel.getAnnotations().isEmpty()) {
                    //There is no point to continue for model without annotations
                    continue;
                }
                try {
                    sbmlModelBlockingQueue.put(sbmlModel);
                    Thread.sleep(1000);
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
