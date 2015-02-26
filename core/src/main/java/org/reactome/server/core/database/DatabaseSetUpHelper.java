package org.reactome.server.core.database;

import java.util.logging.Logger;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class DatabaseSetUpHelper {
    final static Logger logger = Logger.getLogger(DatabaseSetUpHelper.class.getName());

    public static void dropSchema() {
        Models2PathwayDAOImpl.dropXReferencesTable();
        Models2PathwayDAOImpl.dropBioModelsTable();
        Models2PathwayDAOImpl.dropPathwaysTable();
    }

    public static void createSchema() {
        Models2PathwayDAOImpl.createPathwaysTable();
        Models2PathwayDAOImpl.createBioModelsTable();
        Models2PathwayDAOImpl.createXReferencesTable();
    }

    public static void createJDBCTemplate() {
        Models2PathwayDAOImpl.createJDBCTemplate();
    }
}
