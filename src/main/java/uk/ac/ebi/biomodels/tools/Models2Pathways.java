package uk.ac.ebi.biomodels.tools;

import uk.ac.ebi.biomodels.datastructure.reactomeanalysisservice.AnalysisResult;
import uk.ac.ebi.biomodels.tools.reactome.AnalysisServiceHandler;
import uk.ac.ebi.biomodels.tools.sbml.SBMLModel;
import uk.ac.ebi.biomodels.tools.sbml.SBMLModelFactory;

/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */
public class Models2Pathways {

    public static void main(String[] args) {
        for (String sbmlID : SBMLModelFactory.getAllModelIdsByAllTaxonomyIds()) {
            System.out.print(sbmlID);
            SBMLModel model = SBMLModelFactory.getSBMLModel(sbmlID);
            if (model.getSbmlModelAnnotations().isEmpty()) {
                //There is no point to continue for models without annotations
                System.out.println(" >> EMPTY");
            } else {
                SBMLModel sbmlModel = SBMLModelFactory.getSBMLModel(sbmlID);
                AnalysisResult analysisResult = AnalysisServiceHandler.getReactomeAnalysisResultBySBMLModel(sbmlModel);
                System.out.println(" >> " + analysisResult.getPathwaysFound() + " pathways found");
            }

        }
    }
}
