package uk.ac.ebi.biomodels.tools.reactome;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.ac.ebi.biomodels.datastructure.reactomeanalysisservice.AnalysisResult;

import java.io.IOException;

/**
 * Created by Maximilian Koch on 30/10/2014.
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
