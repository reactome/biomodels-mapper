package uk.ac.ebi.models2pathways.resources.mapping.reactomeanalysisservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class SpeciesSummary {
    private Long dbId;
    private String name;

    @JsonCreator
    public SpeciesSummary(
            @JsonProperty("dbId") Long dbId,
            @JsonProperty("name") String name) {
        this.dbId = dbId;
        this.name = name;
    }

    public Long getDbId() {
        return dbId;
    }

    public String getName() {
        return name;
    }
}