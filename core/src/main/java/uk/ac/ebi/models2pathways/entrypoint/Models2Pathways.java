package uk.ac.ebi.models2pathways.entrypoint;

import uk.ac.ebi.models2pathways.helper.DatabaseSetUpHelper;
import uk.ac.ebi.models2pathways.model.sbml.SBMLModel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */
public class Models2Pathways {

    public static void main(String[] args) {
        BlockingQueue<SBMLModel> sbmlModelBlockingQueue = new LinkedBlockingDeque<SBMLModel>(10);
        DatabaseSetUpHelper.DropSchema();
        DatabaseSetUpHelper.CreateSchema();
        Producer producer = new Producer(sbmlModelBlockingQueue);
        Consumer consumer = new Consumer(sbmlModelBlockingQueue);
        new Thread(consumer).start();
        new Thread(producer).start();
        System.out.println("Process finished");
    }
}