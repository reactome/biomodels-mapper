package org.reactome.server.models2pathways.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class Pathways {
    @JsonProperty("pathways")
    List<PathwayResult> pathwayResults;

    public Pathways() {
    }

    public List<PathwayResult> getPathwayResults() {
        return pathwayResults;
    }

    public void setPathwayResults(List<PathwayResult> pathwayResults) {
        this.pathwayResults = pathwayResults;
    }

    @Override
    public String toString() {
        return "Pathways{" +
                "pathwayResults=" + pathwayResults +
                '}';
    }
}