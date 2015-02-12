package org.reactome.server.core.helper;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.reactome.server.core.model.sbml.SBMLModel;
import org.reactome.server.core.utils.Annotation;
import org.reactome.server.core.utils.URLBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */
public class AnalysisServiceRequest {
    final static Logger logger = Logger.getLogger(AnalysisServiceRequest.class.getName());

    private static CloseableHttpClient httpclient = HttpClients.createDefault();

    /**
     * POST-Request against Reactome Analysis Service
     *
     * @param sbmlModel
     * @return
     */
    public static HttpResponse requestByModel(SBMLModel sbmlModel) {
        HttpPost httpPost = new HttpPost(URLBuilder.getIdentifiersURL());
        StringEntity stringEntity = null;

        try {
            Set<Annotation> annotations = sbmlModel.getSBMLModelAnnotations();
            String name = sbmlModel.getName();
            String annotationsAsString = Annotation.annotationsToAnalysisFormat(name, annotations);
            stringEntity = new StringEntity(annotationsAsString);
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

    /**
     * GET-Request against Reactome Analysis Service
     *
     * @param token
     * @param resource
     * @return
     */
    public static HttpResponse requestByToken(String token, String resource) {
        URI tokenURL = URLBuilder.getTokenURL(token, resource);
        HttpGet httpGet = new HttpGet(tokenURL);
        HttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}