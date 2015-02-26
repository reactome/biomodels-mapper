package org.reactome.server.core.database;

import java.util.logging.Logger;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class DatabaseSetUpHelper {
    final static Logger logger = Logger.getLogger(DatabaseSetUpHelper.class.getName());

    public static void dropSchema() {
        Models2PathwayDAO.dropXReferencesTable();
        Models2PathwayDAO.dropBioModelsTable();
        Models2PathwayDAO.dropPathwaysTable();
    }

    public static void createSchema() {
        Models2PathwayDAO.createPathwaysTable();
        Models2PathwayDAO.createBioModelsTable();
        Models2PathwayDAO.createXReferencesTable();
    }

    public static void createJDBCTemplate() {
        Models2PathwayDAO.createJDBCTemplate();
    }
}
