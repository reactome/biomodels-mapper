package uk.ac.ebi.models2pathways.tools;

import uk.ac.ebi.models2pathways.database.entitys.BioModel;
import uk.ac.ebi.models2pathways.database.entitys.Pathway;
import uk.ac.ebi.models2pathways.resources.mapping.reactomeanalysisservice.AnalysisResult;
import uk.ac.ebi.models2pathways.resources.mapping.reactomeanalysisservice.PathwaySummary;
import uk.ac.ebi.models2pathways.tools.reactome.AnalysisServiceHandler;
import uk.ac.ebi.models2pathways.tools.sbml.SBMLModel;
import uk.ac.ebi.models2pathways.tools.sbml.SBMLModelFactory;


/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */
public class Models2Pathways {

    public static void main(String[] args) {
        for (String sbmlID : SBMLModelFactory.getAllModelIdsByAllTaxonomyIds()) {
            System.out.print(sbmlID);
            SBMLModel model = SBMLModelFactory.getSBMLModel(sbmlID);
            if (model.getSBMLModelAnnotations().isEmpty()) {
                //There is no point to continue for models without annotations
                System.out.println(" >> No Annotations found");
            } else {
                SBMLModel sbmlModel = SBMLModelFactory.getSBMLModel(sbmlID);
                AnalysisResult analysisResult = AnalysisServiceHandler.getReactomeAnalysisResultBySBMLModel(sbmlModel);
                if (analysisResult == null) {
                    int tries = 2;
                    while (analysisResult == null && tries != 0) {
                        analysisResult = AnalysisServiceHandler.getReactomeAnalysisResultBySBMLModel(sbmlModel);
                        tries = tries - 1;
                        if (analysisResult == null && tries == 0) {
                            System.out.println(" >> Couldn't request analysis result");
                        }
                    }
                } else if (analysisResult.getPathwaysFound() == 0) {
                    System.out.println(" >> No pathways found");
                } else {
                    BioModel bioModel = DatabaseHandler.createBioModel(sbmlModel);
                    for(PathwaySummary pathwaySummary : analysisResult.getPathways()){
                        Pathway pathway = DatabaseHandler.createPathway(pathwaySummary);
                        DatabaseHandler.createXReference(bioModel, pathway);
                    }
                    System.out.println(" >> " + analysisResult.getPathwaysFound() + " pathways found");
                }
            }

        }
    }
}
