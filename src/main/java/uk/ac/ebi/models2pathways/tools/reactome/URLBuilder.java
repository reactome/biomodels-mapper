package uk.ac.ebi.models2pathways.tools.reactome;

import java.net.URI;

/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */
public class URLBuilder {

    public static URI getIdentifiersURL() {
        return URI.create("http://reactomedev.oicr.on.ca/AnalysisService/identifiers/?pageSize=0&page=0");
    }

    public static URI getTokenURL(String token, String resource) {
        return URI.create("http://reactomedev.oicr.on.ca/AnalysisService/token/"
                + token + "?resource=" + resource);
    }
}
