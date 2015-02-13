package org.reactome.server.core.helper;

import org.reactome.server.core.entrypoint.Models2Pathways;
import org.reactome.server.core.model.sbml.SBMLModel;

import java.util.Set;
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
            Set<String> allModelIdsByAllTaxonomyIds = SBMLModelFactory.getAllModelIdsByAllTaxonomyIds();
            int x = 1;
            System.err.println("All ModelIdsByAllTaxonomyIds " + allModelIdsByAllTaxonomyIds.size());
            for (String sbmlID : allModelIdsByAllTaxonomyIds) {
                System.err.println(sbmlID);
                System.err.println("Number " + x + " of " + allModelIdsByAllTaxonomyIds.size());
                x++;
                SBMLModel sbmlModel;
                try{
                    sbmlModel = (SBMLModelFactory.getSBMLModel(sbmlID));
                } catch (NullPointerException e) {
                    continue;
                }
                if (sbmlModel.getSBMLModelAnnotations().isEmpty()) {
                    //There is no point to continue for models without annotations
                    continue;
                }
                try {
                    sbmlModelBlockingQueue.put(sbmlModel);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Thread.sleep(2000);
            }
            Models2Pathways.setProducerFinished(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Thread.currentThread().interrupt();
        }
    }
}
