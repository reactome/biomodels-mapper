package org.reactome.server.core.database;

import org.reactome.server.core.model.sbml.SBMLModel;
import org.reactome.server.core.model.reactome.PathwaySummary;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class DatabaseInsertionHelper {
    
    public static void createNewPathwayEntry(PathwaySummary pathwaySummary) {
        Models2PathwayDAO models2PathwayDAO = new Models2PathwayDAO();
        models2PathwayDAO.insertPathway(pathwaySummary.getStId(), pathwaySummary.getName());
    }

    public static void createNewBioModelEntry(SBMLModel sbmlModel) {
        Models2PathwayDAO models2PathwayDAO = new Models2PathwayDAO();
        models2PathwayDAO.insertBioModel(sbmlModel.getBioModelsID(), sbmlModel.getName(), Arrays.toString(sbmlModel.getAuthors()));

    }

    public static void createNewXReferenceEntry(PathwaySummary pathwaySummary, SBMLModel sbmlModel, boolean hasMinPValue, boolean hasApproval) {
        Models2PathwayDAO models2PathwayDAO = new Models2PathwayDAO();
        models2PathwayDAO.insertXReference(pathwaySummary.getStId(), sbmlModel.getBioModelsID(), pathwaySummary.getEntities().getpValue(), pathwaySummary.getEntities().getFdr(),
                pathwaySummary.getEntities().getResource(), pathwaySummary.getReactions().getTotal(), pathwaySummary.getReactions().getFound(), pathwaySummary.getEntities().getTotal(),
                pathwaySummary.getEntities().getFound(), pathwaySummary.getSpecies().getName(), hasMinPValue, hasApproval);
        System.out.println("created xReference");

    }
}