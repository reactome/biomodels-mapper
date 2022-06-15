package org.reactome.server.models2pathways;

import com.martiansoftware.jsap.JSAPResult;
import org.reactome.server.models2pathways.biomodels.fetcher.BioModelFetcher;
import org.reactome.server.models2pathways.biomodels.model.BioModel;
import org.reactome.server.models2pathways.core.helper.*;
import org.reactome.server.models2pathways.core.utils.FileExporter;
import org.reactome.server.models2pathways.core.utils.JSAPHandler;
import org.reactome.server.models2pathways.core.utils.PropertiesLoader;
import org.reactome.server.models2pathways.reactome.helper.AnalysisCoreHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */
public class Main {

    private static Logger logger = LoggerFactory.getLogger("m2pLogger");

    final static int BLOCKING_QUEUE_SIZE = 10;

    private static Thread PRODUCER;

    public static void main(String[] args) {
        JSAPResult jsapResult = JSAPHandler.ArgumentHandler(args);

        logger.info("Process has been started");
        //HierarchiesDataContainer.POOL_SIZE = 10;
        //Set up all given arguments.
        FileExporter.setLocationPath(jsapResult.getString("output"));
        AnalysisCoreHelper.setStructure(jsapResult.getString("reactome"));
        String bioModels = jsapResult.getString("biomodels");
        if (!jsapResult.getBoolean("skip-fetch")) {
            new BioModelFetcher(new File(bioModels)).fetch();
        }
        Producer.setPath(bioModels);

        //Load static properties files.
        //TODO: make those as profile. 
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        try {
            NameSpaceHelper.getInstance().setNamespaces(propertiesLoader.getNamespaces());
            SpeciesHelper.getInstance().setSpecies(propertiesLoader.getSpecies());
            TrivialChemicalHelper.getInstance().setTrivialChemicals(propertiesLoader.getTrivialChemicals());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Setting up output file
        FileExporter.createFile();

        //Shared blockingqueue for producert consumer.
        BlockingQueue<BioModel> bioModelBlockingQueue = new LinkedBlockingDeque<>(BLOCKING_QUEUE_SIZE);

        //Let's go... starting threads
        Producer producer = new Producer(bioModelBlockingQueue);

        Main.PRODUCER = new Thread(producer);
        Main.PRODUCER.start();
        logger.info("Producer process has been started");

        Consumer consumer = null;
        if (jsapResult.getString("extendedFDR") == null) {
            consumer = new Consumer(bioModelBlockingQueue, jsapResult.getDouble("significantFDR"), jsapResult.getDouble("coverage"));

        } else {
            if (jsapResult.getDouble("significantFDR") < jsapResult.getDouble("extendedFDR")) {
                consumer = new Consumer(bioModelBlockingQueue, jsapResult.getDouble("significantFDR"), jsapResult.getDouble("extendedFDR"), jsapResult.getString("coverage"));
            } else {
                logger.info("significantFDR is bigger than extendedFDR");
                System.exit(1);
            }
        }
        try {
            new Thread(consumer).start();
        } catch (NullPointerException e) {
            logger.info("NullPointerException on starting consumer thread");
            e.printStackTrace();
            System.exit(1);
        }
        logger.info("Consumer process has been started");
    }

    public static boolean isProducerAlive() {
        return Main.PRODUCER.isAlive();
    }
}