package uk.ac.ebi.models2pathways.tools.reactome;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import uk.ac.ebi.models2pathways.resources.mapping.reactomeanalysisservice.AnalysisResult;
import uk.ac.ebi.models2pathways.resources.mapping.reactomeanalysisservice.PathwaySummary;
import uk.ac.ebi.models2pathways.resources.mapping.reactomeanalysisservice.ResourceSummary;
import uk.ac.ebi.models2pathways.tools.sbml.SBMLModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                String token = analysisResult.getSummary().getToken();
                String resourceSummary = getResource(analysisResult.getResourceSummary());
                httpResponse = AnalysisServiceRequest.requestByToken(token, resourceSummary);
                try {
                    HttpEntity entity = httpResponse.getEntity();
                    jsonResult = EntityUtils.toString(entity, "UTF-8");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                analysisResult = AnalysisResultConverter.getAnalysisResultObject(jsonResult);
            }

            analysisResult.setPathways(getReliablePathways(analysisResult.getPathways()));
            return analysisResult;
        }
        return null;
    }

    private static List<PathwaySummary> getReliablePathways(List<PathwaySummary> pathways) {
        List<PathwaySummary> reliablePathways = new ArrayList<PathwaySummary>();
        for (PathwaySummary pathway : pathways) {
            if (pathway.isLlp() && pathway.getEntities().getpValue() <= 0.005) {
                reliablePathways.add(pathway);
            }
        }
        return reliablePathways;
    }

    private static String getResource(List<ResourceSummary> resourceSummary) {
        ResourceSummary rs = resourceSummary.size() == 2 ? resourceSummary.get(1) : resourceSummary.get(0);
        return rs.getResource();
    }
}
