package org.reactome.server.models2pathways.service.hepler;

import org.reactome.server.models2pathways.service.database.Models2PathwayDAO;
import org.reactome.server.models2pathways.service.model.BioModelsIdentifier;
import org.reactome.server.models2pathways.service.model.PathwayIdentifier;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class JSONObjFactory {
    
    public static BioModelsIdentifier getBioModelsIdentifier(String pathwayIdentifier){
        Models2PathwayDAO models2PathwayDAO = new Models2PathwayDAO();
        BioModelsIdentifier bioModelsIdentifier = new BioModelsIdentifier();
        bioModelsIdentifier.setBioModelsIdentifiers(models2PathwayDAO.getAllBioModelsIdentifier(pathwayIdentifier));
        models2PathwayDAO.getAmount();
        return bioModelsIdentifier;
    }
    
    public static PathwayIdentifier getPathwayIdentifier(String bioModelIdentifier){
        Models2PathwayDAO models2PathwayDAO = new Models2PathwayDAO();
        PathwayIdentifier pathwayIdentifier = new PathwayIdentifier();
        pathwayIdentifier.setPathwayIdentifiers(models2PathwayDAO.getAllPathwayIdentifier(bioModelIdentifier));
        return pathwayIdentifier;
    }
}
