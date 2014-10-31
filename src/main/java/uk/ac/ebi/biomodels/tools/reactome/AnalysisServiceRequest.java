package uk.ac.ebi.biomodels.tools.reactome;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import uk.ac.ebi.biomodels.datastructure.sbml.Annotation;
import uk.ac.ebi.biomodels.enums.Resource;
import uk.ac.ebi.biomodels.tools.sbml.SBMLModel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Set;

/**
 * Created by Maximilian Koch on 30/10/2014.
 */
public class AnalysisServiceRequest {
    private static CloseableHttpClient httpclient = HttpClients.createDefault();
    ;
    private static HttpPost httpPost = new HttpPost();

    public static String requestByModel(SBMLModel sbmlModel) {
        //URI url = URI.create("http://www.reactome.org:80/AnalysisService/identifiers/?pageSize=20&page=1&sortBy=ENTITIES_PVALUE&order=ASC&resource=TOTAL");
        httpPost.setURI(URLBuilder.getIdentifiersURL());
        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(annotationsToAnalysisFormat(sbmlModel.getSbmlModelAnnotations()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpPost.setEntity(stringEntity);
        HttpResponse response = null;
        try {
        response = httpclient.execute(httpPost);
        String json = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public void requestByToken(String token, Resource resource) {

    }

    private static String annotationsToAnalysisFormat(Set<Annotation> annotations) {
        StringBuilder annotationsInAnalysisFormat = new StringBuilder();
        for (Annotation annotation : annotations) {
            annotationsInAnalysisFormat.append(annotation.getEntityId()).append(System.getProperty("line.separator"));
        }
        return String.valueOf(annotationsInAnalysisFormat);
    }
}
