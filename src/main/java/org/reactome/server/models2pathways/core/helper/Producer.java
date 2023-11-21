package org.reactome.server.models2pathways.core.helper;

import org.reactome.server.models2pathways.biomodels.helper.BioModelHelper;
import org.reactome.server.models2pathways.biomodels.model.BioModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class Producer implements Runnable {
    private static Logger logger = LoggerFactory.getLogger("m2pLogger");
    private static String path;
    private BlockingQueue<BioModel> bioModelBlockingQueue;

    public Producer(BlockingQueue<BioModel> bioModelBlockingQueue) {
        this.bioModelBlockingQueue = bioModelBlockingQueue;
    }

    public static void setPath(String path) {
        Producer.path = path;
    }

    @Override
    public void run() {
        try {
            File files[] = new File(path).listFiles();
            if (files == null) {
                logger.info("No files in BioModels-Folder");
                Thread.currentThread().interrupt();
                System.exit(1);
            }
            for (File file : files) {
                if (file.getName().startsWith("BIOMD")) {
                    if (!file.getName().endsWith(".xml") && !file.getName().endsWith(".sbml")) {
                        logger.warn(file.getName()  + " skipped since its format is not supported (only supporting SBML)");
                        continue;
                    }
                    try {
                        BioModel bioModel = BioModelHelper.getBioModelByBioModelId(file);
                        if (bioModel.getSpecie() != null && SpeciesHelper.getInstance().getSpecies().contains(bioModel.getSpecie())) {
                            bioModelBlockingQueue.put(BioModelHelper.getBioModelByBioModelId(file));
                        }
                    } catch (Exception e) {
                        logger.error(file.getName()  + " produced error :" + e.getMessage(), e);
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            Thread.currentThread().interrupt();
            logger.info("Producer has finished");
        }
    }
}
