package uk.ac.ebi.biomodels.datastructure.reactomeanalysisservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class EntityStatistics extends Statistics {
    private Double pValue;
    private Double fdr;
    private List<Double> exp = null;

    @JsonCreator
    public EntityStatistics(
            @JsonProperty("resource") String resource,
            @JsonProperty("total") Integer total,
            @JsonProperty("found") Integer found,
            @JsonProperty("ratio") Double ratio,
            @JsonProperty("pValue") Double pValue,
            @JsonProperty("fdr") Double fdr,
            @JsonProperty("exp") List<Double> exp) {
        super(resource, total, found, ratio);
        this.pValue = pValue;
        this.fdr = fdr;
        this.exp = exp;
    }

    public Double getpValue() {
        return pValue;
    }

    public void setpValue(Double pValue) {
        this.pValue = pValue;
    }

    public Double getFdr() {
        return fdr;
    }

    public void setFdr(Double fdr) {
        this.fdr = fdr;
    }

    public List<Double> getExp() {
        return exp;
    }

    public void setExp(List<Double> exp) {
        this.exp = exp;
    }
}
