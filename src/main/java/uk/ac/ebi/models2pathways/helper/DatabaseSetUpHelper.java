package uk.ac.ebi.models2pathways.helper;

import uk.ac.ebi.models2pathways.database.Models2PathwayDAO;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class DatabaseSetUpHelper {
    public static void DropSchema(){
        Models2PathwayDAO models2PathwayDAO = new Models2PathwayDAO();
        models2PathwayDAO.dropXReferencesTable();
        models2PathwayDAO.dropBioModelsTable();
        models2PathwayDAO.dropPathwaysTable();
    }
    
    public static void CreateSchema(){
        Models2PathwayDAO models2PathwayDAO = new Models2PathwayDAO();
        models2PathwayDAO.createPathwaysTable();
        models2PathwayDAO.createBioModelsTable();
        models2PathwayDAO.createXReferencesTable();
    }
}
