package uk.ac.ebi.biomodels.datastructure.reactomeanalysisservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
//@ApiModel(value = "PathwaySummary", description = "Contains general information about a certain pathway")
public class PathwaySummary {
    //    @ApiModelProperty(value = "The pathway stable identifier", notes = "", required = true )
    private String stId;
    //    @ApiModelProperty(value = "The pathway database identifier", notes = "", required = true )
    private Long dbId;
    //    @ApiModelProperty(value = "The pathway name", notes = "", required = true )
    private String name;
    //    @ApiModelProperty(value = "The pathway species", notes = "", required = true )
    private SpeciesSummary species;
    //    @ApiModelProperty(value = "Whether it is a lower level pathway or not", notes = "", required = true )
    private boolean llp; //lower level pathway

//    private Long speciesDbId;

//    private String species;

    //    @ApiModelProperty(value = "Statistics for the found entities in this pathway", notes = "", required = true )
    private EntityStatistics entities;
    //    @ApiModelProperty(value = "Statistics for the found reactions in this pathway", notes = "", required = true )
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

        if (dbId != null ? !dbId.equals(that.dbId) : that.dbId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return dbId != null ? dbId.hashCode() : 0;
    }
}