package org.reactome.server.core.database;

import org.reactome.server.core.model.reactome.PathwaySummary;
import org.reactome.server.core.model.sbml.SBMLModel;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class DatabaseInsertionHelper {
    
    public static void createNewPathwayEntry(PathwaySummary pathwaySummary) {
        Models2PathwayDAOImpl.insertPathway(pathwaySummary.getStId(), pathwaySummary.getName());
    }

    public static void createNewBioModelEntry(SBMLModel sbmlModel) {
        Models2PathwayDAOImpl.insertBioModel(sbmlModel.getBioModelsID(), sbmlModel.getName());

    }

    public static void createNewXReferenceEntry(PathwaySummary pathwaySummary, SBMLModel sbmlModel, boolean hasMinPValue, boolean hasApproval) {
        Models2PathwayDAOImpl.insertXReference(pathwaySummary.getStId(), sbmlModel.getBioModelsID(), pathwaySummary.getEntities().getpValue(), pathwaySummary.getEntities().getFdr(),
                pathwaySummary.getEntities().getResource(), pathwaySummary.getReactions().getTotal(), pathwaySummary.getReactions().getFound(), pathwaySummary.getEntities().getTotal(),
                pathwaySummary.getEntities().getFound(), pathwaySummary.getSpecies().getName(), hasMinPValue, hasApproval);
    }
}