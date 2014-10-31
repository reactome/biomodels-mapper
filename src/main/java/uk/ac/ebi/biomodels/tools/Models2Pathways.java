package uk.ac.ebi.biomodels.tools;

import uk.ac.ebi.biomodels.tools.reactome.AnalysisServiceHandler;
import uk.ac.ebi.biomodels.tools.sbml.SBMLModelFactory;

/**
 * Created by Maximilian Koch on 23/10/2014.
 */
public class Models2Pathways {
    //private static final Logger logger = LoggerFactory.getLogger(m2p.class);

    public static void main(String[] args) {
//        for(String sbmlID : SBMLModelFactory.getAllModelIdsByAllTaxonomyIds()){
//            System.out.println(sbmlID);
//            if(SBMLModelFactory.getSBMLModel(sbmlID).getBioModelsID().equals("BIOMD0000000015")){
//                AnalysisService.requestByModel(SBMLModelFactory.getSBMLModel(sbmlID));
//            }
//        }

        AnalysisServiceHandler.queryAnalysisService(SBMLModelFactory.getSBMLModel("BIOMD0000000015"));
    }
}
