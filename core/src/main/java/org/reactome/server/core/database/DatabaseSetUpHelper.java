package org.reactome.server.core.database;

import java.util.logging.Logger;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class DatabaseSetUpHelper {
    final static Logger logger = Logger.getLogger(DatabaseSetUpHelper.class.getName());

    public static void DropSchema() {
        Models2PathwayDAO models2PathwayDAO = new Models2PathwayDAO();
        models2PathwayDAO.dropXReferencesTable();
        models2PathwayDAO.dropBioModelsTable();
        models2PathwayDAO.dropPathwaysTable();
        logger.info("All tables successfully deleted");
    }

    public static void CreateSchema() {
        Models2PathwayDAO models2PathwayDAO = new Models2PathwayDAO();
        models2PathwayDAO.createPathwaysTable();
        models2PathwayDAO.createBioModelsTable();
        models2PathwayDAO.createXReferencesTable();
        logger.info("All tables successfully created");
    }
}
