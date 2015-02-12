package org.reactome.server.core.model.reactome;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class PathwaySummary {
    private String stId;
    private Long dbId;
    private String name;
    private SpeciesSummary species;
    private boolean llp; //lower level pathway
    private EntityStatistics entities;
    private ReactionStatistics reactions;

    @JsonCreator
    public PathwaySummary(
            @JsonProperty("stId") String stId,
            @JsonProperty("dbId") Long dbId,
            @JsonProperty("name") String name,
            @JsonProperty("species") SpeciesSummary species,
            @JsonProperty("llp") boolean llp,
            @JsonProperty("entities") EntityStatistics entities,
            @JsonProperty("reactions") ReactionStatistics reactions) {
        this.stId = stId;
        this.dbId = dbId;
        this.name = name;
        this.species = species;
        this.llp = llp;
        this.entities = entities;
        this.reactions = reactions;
    }

    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SpeciesSummary getSpecies() {
        return species;
    }

    public void setSpecies(SpeciesSummary species) {
        this.species = species;
    }

    public boolean isLlp() {
        return llp;
    }

    public void setLlp(boolean llp) {
        this.llp = llp;
    }

    public EntityStatistics getEntities() {
        return entities;
    }

    public void setEntities(EntityStatistics entities) {
        this.entities = entities;
    }

    public ReactionStatistics getReactions() {
        return reactions;
    }

    public void setReactions(ReactionStatistics reactions) {
        this.reactions = reactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PathwaySummary that = (PathwaySummary) o;

        return !(dbId != null ? !dbId.equals(that.dbId) : that.dbId != null);

    }

    @Override
    public int hashCode() {
        return dbId != null ? dbId.hashCode() : 0;
    }
}