package org.reactome.server.models2pathways.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class Result {
    @JsonProperty("id")
    private String id;
    @JsonProperty("pValue")
    private double pValue;
    @JsonProperty("FDR")
    private double fdr;

    public Result() {
    }

    public Result(String id, double pValue, double fdr) {
        this.id = id;
        this.pValue = pValue;
        this.fdr = fdr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getpValue() {
        return pValue;
    }

    public void setpValue(double pValue) {
        this.pValue = pValue;
    }

    public double getFdr() {
        return fdr;
    }

    public void setFdr(double fdr) {
        this.fdr = fdr;
    }
}
