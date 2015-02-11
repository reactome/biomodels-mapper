package org.reactome.server.models2pathways.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class PathwayIdentifier {
    @JsonProperty("pathwayIdentifiers")
    Set<String> pathwayIdentifiers;

    public PathwayIdentifier() {
    }

    public Set<String> getPathwayIdentifiers() {
        return pathwayIdentifiers;
    }

    public void setPathwayIdentifiers(Set<String> pathwayIdentifiers) {
        this.pathwayIdentifiers = pathwayIdentifiers;
    }

    @Override
    public String toString() {
        return "PathwayIdentifier{" +
                ", pathwayIdentifiers=" + pathwayIdentifiers +
                '}';
    }
}