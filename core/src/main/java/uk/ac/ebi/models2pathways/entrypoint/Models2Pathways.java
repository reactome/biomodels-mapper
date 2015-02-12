package uk.ac.ebi.models2pathways.entrypoint;

import com.martiansoftware.jsap.JSAPResult;
import uk.ac.ebi.models2pathways.database.DataSourceFactory;
import uk.ac.ebi.models2pathways.helper.Consumer;
import uk.ac.ebi.models2pathways.helper.DatabaseSetUpHelper;
import uk.ac.ebi.models2pathways.helper.Producer;
import uk.ac.ebi.models2pathways.model.sbml.SBMLModel;
import uk.ac.ebi.models2pathways.utils.JSAPHandler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */
public class Models2Pathways {
    private static boolean producerFinished;

    public static void main(String[] args) {
        JSAPResult jsapResult = JSAPHandler.ArgumentHandler(args);
        System.out.println(jsapResult.getString("database"));
        DataSourceFactory.setDatabaseLocation(jsapResult.getString("database"));
        DataSourceFactory.setUsername(jsapResult.getString("username"));
        DataSourceFactory.setPassword(jsapResult.getString("password"));

        BlockingQueue<SBMLModel> sbmlModelBlockingQueue = new LinkedBlockingDeque<SBMLModel>(3);
        DatabaseSetUpHelper.DropSchema();
        DatabaseSetUpHelper.CreateSchema();
        Producer producer = new Producer(sbmlModelBlockingQueue);
        Consumer consumer = new Consumer(sbmlModelBlockingQueue, jsapResult.getString("significantPValue"), jsapResult.getString("extendedPValue"));
        new Thread(consumer).start();
        new Thread(producer).start();
        System.out.println("Process finished");
    }

    public static boolean isProducerFinished() {
        return producerFinished;
    }

    public static void setProducerFinished(boolean producerFinished) {
        Models2Pathways.producerFinished = producerFinished;
    }
}