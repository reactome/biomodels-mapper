package org.reactome.server.widget.model;

import java.util.Set;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class BioModelsIdentifier {
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
