package org.reactome.server.core.entrypoint;

import com.martiansoftware.jsap.JSAPResult;
import org.reactome.server.core.database.DataSourceFactory;
import org.reactome.server.core.helper.Consumer;
import org.reactome.server.core.database.DatabaseSetUpHelper;
import org.reactome.server.core.helper.Producer;
import org.reactome.server.core.model.sbml.SBMLModel;
import org.reactome.server.core.utils.JSAPHandler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;


/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */
public class Models2Pathways {
    final static Logger logger = Logger.getLogger(Models2Pathways.class.getName());
    final static int BLOCKING_QUEUE_SIZE = 3;

    //boolean for killing consumer thread
    private static boolean producerFinished;

    public static void main(String[] args) {
        logger.info("Process has been started");
        //Set up all given arguments.
        JSAPResult jsapResult = JSAPHandler.ArgumentHandler(args);
        DataSourceFactory.setDatabaseLocation(jsapResult.getString("database"));
        DataSourceFactory.setUsername(jsapResult.getString("username"));
        DataSourceFactory.setPassword(jsapResult.getString("password"));

        //Shared blockingqueue for producert consumer.
        BlockingQueue<SBMLModel> sbmlModelBlockingQueue = new LinkedBlockingDeque<SBMLModel>(BLOCKING_QUEUE_SIZE);
        //Database set up
        DatabaseSetUpHelper.DropSchema();
        DatabaseSetUpHelper.CreateSchema();

        //Let's go... starting threads
        Producer producer = new Producer(sbmlModelBlockingQueue);
        Consumer consumer = new Consumer(sbmlModelBlockingQueue, jsapResult.getString("significantPValue"), jsapResult.getString("extendedPValue"));
        new Thread(consumer).start();
        new Thread(producer).start();
    }

    public static boolean isProducerFinished() {
        return producerFinished;
    }

    public static void setProducerFinished(boolean producerFinished) {
        Models2Pathways.producerFinished = producerFinished;
    }
}