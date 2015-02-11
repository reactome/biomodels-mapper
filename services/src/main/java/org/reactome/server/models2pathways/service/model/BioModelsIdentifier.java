package org.reactome.server.models2pathways.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class BioModelsIdentifier {
    @JsonProperty("bioModelsIdentifiers")
    private Set<String> bioModelsIdentifiers;

    public BioModelsIdentifier() {
    }

    public Set<String> getBioModelsIdentifiers() {
        return bioModelsIdentifiers;
    }

    public void setBioModelsIdentifiers(Set<String> bioModelsIdentifiers) {
        this.bioModelsIdentifiers = bioModelsIdentifiers;
    }

    @Override
    public String toString() {
        return "BioModelsIdentifier{" +
                ", bioModelsIdentifiers=" + bioModelsIdentifiers +
                '}';
    }
}
