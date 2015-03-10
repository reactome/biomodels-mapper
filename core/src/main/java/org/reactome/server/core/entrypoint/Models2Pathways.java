package org.reactome.server.core.entrypoint;

import com.martiansoftware.jsap.JSAPResult;
import org.reactome.server.core.database.DataSourceFactory;
import org.reactome.server.core.database.DatabaseSetUpHelper;
import org.reactome.server.core.helper.Consumer;
import org.reactome.server.core.helper.Producer;
import org.reactome.server.core.model.sbml.SBMLModel;
import org.reactome.server.core.output.FileHandler;
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

    private static Thread PRODUCER;

    public static void main(String[] args) {
        logger.info("Process has been started");
        //Set up all given arguments.
        JSAPResult jsapResult = JSAPHandler.ArgumentHandler(args);
        DataSourceFactory.setDatabaseLocation(jsapResult.getString("database"));
        DataSourceFactory.setUsername(jsapResult.getString("username"));
        DataSourceFactory.setPassword(jsapResult.getString("password"));
        FileHandler.setLocationPath(jsapResult.getString("output"));

        logger.info("Database location has been set: " + DataSourceFactory.getDatabaseLocation());

        //Setting up output file
        if (!jsapResult.getString("output").isEmpty()) {
            FileHandler.createFile();
            FileHandler.addHeader();
        }

        //Shared blockingqueue for producert consumer.
        BlockingQueue<SBMLModel> sbmlModelBlockingQueue = new LinkedBlockingDeque<>(BLOCKING_QUEUE_SIZE);

        if (jsapResult.getString("username") != null || jsapResult.getString("password") != null || jsapResult.getString("database") != null) {
            //Database set up
            DatabaseSetUpHelper.createJDBCTemplate();
            DatabaseSetUpHelper.dropSchema();
            DatabaseSetUpHelper.createSchema();
            logger.info("Database has been dropped and new created");
        }


        //Let's go... starting threads
        Producer producer = new Producer(sbmlModelBlockingQueue);

        Models2Pathways.PRODUCER = new Thread(producer);
        Models2Pathways.PRODUCER.start();
        logger.info("Started producer process");

        Consumer consumer = new Consumer(sbmlModelBlockingQueue, jsapResult.getString("significantFDR"), jsapResult.getString("extendedFDR"), jsapResult.getString("coverage"));
        new Thread(consumer).start();
        logger.info("Started consumer process");
    }

    public static boolean isProducerAlive() {
        return Models2Pathways.PRODUCER.isAlive();
    }
}