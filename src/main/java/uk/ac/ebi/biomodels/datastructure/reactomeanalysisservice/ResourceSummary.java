package uk.ac.ebi.biomodels.datastructure.reactomeanalysisservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class ResourceSummary implements Comparable<ResourceSummary> {
    String resource;
    Integer pathways;

    @JsonCreator
    public ResourceSummary(
            @JsonProperty("resource") String resource,
            @JsonProperty("pathways") Integer pathways) {
        this.resource = resource;
        this.pathways = pathways;
    }

    public String getResource() {
        return resource;
    }

    public Integer getPathways() {
        return pathways;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResourceSummary that = (ResourceSummary) o;

        return !(resource != null ? !resource.equals(that.resource) : that.resource != null);

    }

    @Override
    public int hashCode() {
        return resource != null ? resource.hashCode() : 0;
    }

    @Override
    public int compareTo(ResourceSummary o) {
        return pathways.compareTo(o.pathways);
    }
}