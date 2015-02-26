package org.reactome.server.core.helper;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.reactome.server.core.enums.Species;
import org.reactome.server.core.model.reactome.AnalysisResult;
import org.reactome.server.core.model.reactome.PathwaySummary;
import org.reactome.server.core.model.reactome.ResourceSummary;
import org.reactome.server.core.model.sbml.SBMLModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */
public class AnalysisServiceHandler {
    final static Logger logger = Logger.getLogger(AnalysisServiceHandler.class.getName());

    public static AnalysisResult getReactomeAnalysisResultBySBMLModel(SBMLModel sbmlModel, Double customPValue) {
        HttpResponse httpResponse = AnalysisServiceRequest.requestByModel(sbmlModel);
        String token = null;
        String jsonResult = null;
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            try {
                jsonResult = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            AnalysisResult analysisResult = getAnalysisResultObject(jsonResult);
            if (analysisResult.getPathwaysFound() != 0) {
                token = analysisResult.getSummary().getToken();
                String resourceSummary = getResource(analysisResult.getResourceSummary());
                httpResponse = AnalysisServiceRequest.requestByToken(token, resourceSummary);
                try {
                    HttpEntity entity = httpResponse.getEntity();
                    jsonResult = EntityUtils.toString(entity, "UTF-8");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                analysisResult = getAnalysisResultObject(jsonResult);
            }

            analysisResult.setPathways(getReliablePathways(analysisResult.getPathways(), customPValue, sbmlModel));
            analysisResult.setToken(token);
            return analysisResult;
        }
        return null;
    }

    private static List<PathwaySummary> getReliablePathways(List<PathwaySummary> pathways, Double customPValue, SBMLModel sbmlModel) {
        List<PathwaySummary> reliablePathways = new ArrayList<PathwaySummary>();
        for (PathwaySummary pathway : pathways) {
            if (pathway.isLlp() && pathway.getEntities().getpValue() <= customPValue) {
                try {
                    if (pathway.getSpecies().getDbId().toString().equals(Species.getSpeciesByBioModelsTaxonomyid(sbmlModel.getBioModelsTaxonomyId().getBioModelsTaxonomyId()).getReactomeTaxonomyId()))
                        reliablePathways.add(pathway);
                } catch (NullPointerException e) {
                    continue;
                }

            }
        }
        return reliablePathways;
    }

    public static String getResource(List<ResourceSummary> resourceSummary) {
        ResourceSummary rs = resourceSummary.size() == 2 ? resourceSummary.get(1) : resourceSummary.get(0);
        return rs.getResource();
    }

    private static AnalysisResult getAnalysisResultObject(String jsonString) {
        AnalysisResult analysisResult = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            analysisResult = mapper.readValue(jsonString, AnalysisResult.class);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return analysisResult;
    }
}
