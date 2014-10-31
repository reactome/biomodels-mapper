package uk.ac.ebi.biomodels.tools.reactome;

import uk.ac.ebi.biomodels.datastructure.reactomeanalysisservice.AnalysisResult;
import uk.ac.ebi.biomodels.datastructure.reactomeanalysisservice.PathwaySummary;
import uk.ac.ebi.biomodels.datastructure.reactomeanalysisservice.ResourceSummary;
import uk.ac.ebi.biomodels.enums.Resource;
import uk.ac.ebi.biomodels.tools.sbml.SBMLModel;

/**
 * Created by Maximilian Koch on 30/10/2014.
 */
public class AnalysisServiceHandler {

    public static AnalysisResult getReactomeAnalysisResultBySBMLModel(SBMLModel sbmlModel) {

    }

    private static String queryAnalysisService(SBMLModel sbmlModel) {
        return AnalysisServiceRequest.requestByModel(sbmlModel);
    }

    private static String queryAnalysisService(SBMLModel sbmlModel, String token, Resource resource) {
        return AnalysisServiceRequest.requestByModel(sbmlModel);
    }

    private boolean requestWithToken(ResourceSummary resourceSummary) {

    }

    private boolean isLowerLevelPathway(PathwaySummary pathwaySummary) {

    }

    private boolean hasLowerFDR(PathwaySummary pathwaySummary) {

    }
}
