package uk.ac.ebi.models2pathways.helper;

import uk.ac.ebi.models2pathways.database.Models2PathwayDAO;
import uk.ac.ebi.models2pathways.model.reactome.PathwaySummary;
import uk.ac.ebi.models2pathways.model.sbml.SBMLModel;

import java.util.Arrays;

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

    }
}