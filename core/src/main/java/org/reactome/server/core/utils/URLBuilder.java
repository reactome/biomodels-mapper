package org.reactome.server.core.utils;

import java.net.URI;
import java.util.Properties;

/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */
public class URLBuilder {

    private static final PropertiesHelper propertiesHelper = new PropertiesHelper();


    public static URI getIdentifiersURL() {
        Properties properties = getProperties();
        return URI.create(properties.getProperty("analysis.service.url"));
    }

    public static URI getTokenURL(String token, String resource) {
        Properties properties = getProperties();

        return URI.create(properties.getProperty("analysis.service.url.token") + token +
                properties.getProperty("analysis.service.url.token.extension") + resource);
    }

    private static Properties getProperties() {
        return propertiesHelper.getAnalysisURLProperties();
    }

    public static URI getPathwaySBMLURL(long dbId) {
        String URL = "http://reactomews.oicr.on.ca:8080/ReactomeRESTfulAPI/RESTfulWS/sbmlExporter/" + dbId;
        return URI.create(URL);
    }

    public static URI getAnalysisURL(Long species, String token) {
        String URL = "http://reactomedev.oicr.on.ca/PathwayBrowser/#SPECIES=" + species.toString() + "&DTAB=AN&ANALYSIS=" + token;
        return URI.create(URL);
    }
}
