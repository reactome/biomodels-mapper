package uk.ac.ebi.biomodels.tools.reactome;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import uk.ac.ebi.biomodels.datastructure.sbml.Annotation;
import uk.ac.ebi.biomodels.tools.sbml.SBMLModel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Set;

/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */
public class AnalysisServiceRequest {
    private static CloseableHttpClient httpclient = HttpClients.createDefault();

    public static HttpResponse requestByModel(SBMLModel sbmlModel) {
        HttpPost httpPost = new HttpPost(URLBuilder.getIdentifiersURL());
        StringEntity stringEntity = null;

        try {
            Set<Annotation> sample = sbmlModel.getSbmlModelAnnotations();
            String name = sbmlModel.getName();
            stringEntity = new StringEntity(annotationsToAnalysisFormat(name, sample));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpPost.setEntity(stringEntity);
        HttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static HttpResponse requestByToken(String token, String resource) {
        HttpGet httpGet = new HttpGet(URLBuilder.getTokenURL(token, resource));
        HttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private static String annotationsToAnalysisFormat(String model, Set<Annotation> annotations) {
        StringBuilder annotationsInAnalysisFormat = new StringBuilder();
        //Adding the name of the model to the sample data for a better identification in the PathwayBrowser result
        annotationsInAnalysisFormat.append("#").append(model).append(System.getProperty("line.separator"));
        for (Annotation annotation : annotations) {
            annotationsInAnalysisFormat.append(annotation.getEntityId()).append(System.getProperty("line.separator"));
        }
        return String.valueOf(annotationsInAnalysisFormat);
    }
}
