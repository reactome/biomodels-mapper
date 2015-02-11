package uk.ac.ebi.models2pathways.entrypoint;

import uk.ac.ebi.models2pathways.helper.AnalysisServiceHandler;
import uk.ac.ebi.models2pathways.helper.DatabaseInsertionHelper;
import uk.ac.ebi.models2pathways.helper.DatabaseSetUpHelper;
import uk.ac.ebi.models2pathways.helper.SBMLModelFactory;
import uk.ac.ebi.models2pathways.model.reactome.AnalysisResult;
import uk.ac.ebi.models2pathways.model.reactome.PathwaySummary;
import uk.ac.ebi.models2pathways.model.sbml.SBMLModel;


/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */
public class Models2Pathways {
    private final static Double significantPValue = 0.05;
    private final static Double extendedPValue = 0.1;

    public static void main(String[] args) {
        DatabaseSetUpHelper.DropSchema();
        DatabaseSetUpHelper.CreateSchema();
        for (String sbmlID : SBMLModelFactory.getAllModelIdsByAllTaxonomyIds()) {
            boolean hasMinPValue = true;
            System.out.print(sbmlID);
            SBMLModel model = SBMLModelFactory.getSBMLModel(sbmlID);
            if (model.getSBMLModelAnnotations().isEmpty()) {
                //There is no point to continue for models without annotations
                System.out.println(" >> No Annotations found");
            } else {
                SBMLModel sbmlModel = SBMLModelFactory.getSBMLModel(sbmlID);
                AnalysisResult analysisResult = AnalysisServiceHandler.getReactomeAnalysisResultBySBMLModel(sbmlModel, significantPValue);
                if (analysisResult == null) {
                    int tries = 2;
                    while (analysisResult == null && tries != 0) {
                        analysisResult = AnalysisServiceHandler.getReactomeAnalysisResultBySBMLModel(sbmlModel, significantPValue);
                        tries = tries - 1;
                        if (analysisResult == null && tries == 0) {
                            System.out.println(" >> Couldn't request analysis result");
                        }
                    }
                } else if (analysisResult.getPathwaysFound() == 0) {
                    System.out.println("Couldn't find pathways on pValue " + significantPValue);
                    System.out.println("Request on extended pValue " + extendedPValue);
                    hasMinPValue = false;
                    analysisResult = AnalysisServiceHandler.getReactomeAnalysisResultBySBMLModel(sbmlModel, extendedPValue);
                    if (analysisResult == null) {
                        int tries = 2;
                        while (analysisResult == null && tries != 0) {
                            analysisResult = AnalysisServiceHandler.getReactomeAnalysisResultBySBMLModel(sbmlModel, extendedPValue);
                            tries = tries - 1;
                            if (analysisResult == null && tries == 0) {
                                System.out.println(" >> Couldn't request analysis result");
                            }
                        }
                    }
                    if (analysisResult == null || analysisResult.getPathwaysFound() == 0) {
                        System.out.println(" >> No pathways found with pValue " + extendedPValue);
                        continue;
                    }
                }
                if (analysisResult != null) {
                    DatabaseInsertionHelper.createNewBioModelEntry(sbmlModel);
                    for (PathwaySummary pathwaySummary : analysisResult.getPathways()) {
                        DatabaseInsertionHelper.createNewPathwayEntry(pathwaySummary);
                        System.out.println(pathwaySummary.getEntities().getpValue());
                        DatabaseInsertionHelper.createNewXReferenceEntry(pathwaySummary, sbmlModel, hasMinPValue, false);
                        System.out.println(" >> " + analysisResult.getPathwaysFound() + " pathways found");
                    }
                }
            }
        }
        System.out.println("Process finished");
    }
}