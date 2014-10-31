package uk.ac.ebi.biomodels.datastructure.reactomeanalysisservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class ReactionStatistics extends Statistics {

    @JsonCreator
    public ReactionStatistics(
            @JsonProperty("resource") String resource,
            @JsonProperty("total") Integer total,
            @JsonProperty("found") Integer found,
            @JsonProperty("ratio") Double ratio) {
        super(resource, total, found, ratio);
    }
}
