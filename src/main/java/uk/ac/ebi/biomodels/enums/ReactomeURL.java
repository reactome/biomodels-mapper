package uk.ac.ebi.biomodels.enums;

/**
 * Created by Maximilian Koch on 30/10/2014.
 */
public enum ReactomeURL {
    BY_IDENTIFIER("http://www.reactome.org:80/AnalysisService/identifiers/"),
    BY_TOKEN("http://www.reactome.org:80/AnalysisService/token/DFSFSDFSDFSDFSFSDFSDSGSADF?pageSize=0&page=1&sortBy=ENTITIES_PVALUE&order=ASC&resource=UNIPROT");

    private String queryURL;

    private ReactomeURL(String queryURL) {
        this.queryURL = queryURL;
    }
}
