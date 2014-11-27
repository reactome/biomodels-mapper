package uk.ac.ebi.models2pathways.tools.reactome;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.ac.ebi.models2pathways.mapping.reactomeanalysisservice.AnalysisResult;

import java.io.IOException;

/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */
public class AnalysisResultConverter {

    public static AnalysisResult getAnalysisResultObject(String jsonString) {
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
