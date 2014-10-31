package uk.ac.ebi.biomodels.tools;

import uk.ac.ebi.biomodels.datastructure.reactomeanalysisservice.AnalysisResult;
import uk.ac.ebi.biomodels.tools.reactome.AnalysisServiceHandler;
import uk.ac.ebi.biomodels.tools.sbml.SBMLModelFactory;

/**
 * Created by Maximilian Koch on 23/10/2014.
 */
public class Models2Pathways {
    //private static final Logger logger = LoggerFactory.getLogger(m2p.class);

    public static void main(String[] args) {
        for(String sbmlID : SBMLModelFactory.getAllModelIdsByAllTaxonomyIds()){
            if(!sbmlID.equals("BIOMD0000000326")) {
                System.out.println(sbmlID);
                AnalysisResult analysisResult = AnalysisServiceHandler.getReactomeAnalysisResultBySBMLModel(SBMLModelFactory.getSBMLModel(sbmlID));

            }
        }
    }
}
