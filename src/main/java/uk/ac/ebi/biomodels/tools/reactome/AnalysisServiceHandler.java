package uk.ac.ebi.biomodels.tools.reactome;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import uk.ac.ebi.biomodels.datastructure.reactomeanalysisservice.AnalysisResult;
import uk.ac.ebi.biomodels.datastructure.reactomeanalysisservice.PathwaySummary;
import uk.ac.ebi.biomodels.datastructure.reactomeanalysisservice.ResourceSummary;
import uk.ac.ebi.biomodels.tools.sbml.SBMLModel;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */
public class AnalysisServiceHandler {

    public static AnalysisResult getReactomeAnalysisResultBySBMLModel(SBMLModel sbmlModel) {
        HttpResponse httpResponse = AnalysisServiceRequest.requestByModel(sbmlModel);
        String jsonResult = null;
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            try {
                jsonResult = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            AnalysisResult analysisResult = AnalysisResultConverter.getAnalysisResultObject(jsonResult);
            if (analysisResult.getPathwaysFound() != 0) {
                if (tokenRequestRequired(analysisResult.getResourceSummary())) {
                    httpResponse = AnalysisServiceRequest.requestByToken(analysisResult.getSummary().getToken(),
                            getResource(analysisResult.getResourceSummary()));
                    try {
                        jsonResult = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    analysisResult = AnalysisResultConverter.getAnalysisResultObject(jsonResult);
                }
            }
            if (onlyNecessaryPathways(analysisResult.getPathways())) {
                return analysisResult;
            }
        }
        return null;
    }

    private static boolean onlyNecessaryPathways(List<PathwaySummary> pathways) {
        Set<PathwaySummary> unnecessaryPathways = new HashSet<PathwaySummary>();
        for (PathwaySummary pathway : pathways) {
            if (!pathway.isLlp()) {
                unnecessaryPathways.add(pathway);
            }
            if (pathway.getEntities().getFdr() >= 0.005) {
                unnecessaryPathways.add(pathway);
            }
        }
        pathways.removeAll(unnecessaryPathways);
        return true;
    }

    private static boolean tokenRequestRequired(List<ResourceSummary> resourceSummary) {
        return resourceSummary.size() < 3;
    }

    private static String getResource(List<ResourceSummary> resourceSummary) {
        return resourceSummary.get(1).getResource();
    }
}
