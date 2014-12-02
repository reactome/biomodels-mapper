package uk.ac.ebi.models2pathways.database.entitys;

/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Pathway")
public class Pathway implements java.io.Serializable {

    private String pathwayId;
    private String name;
    private Set<XReferences> xReferences = new HashSet<XReferences>(0);

    public Pathway() {}

    public Pathway(String name) {
        this.name = name;
    }

    public Pathway(String name, String pathwayId) {
        this.name = name;
        this.pathwayId = pathwayId;
    }

    public Pathway(String pathwayId, String name, Set<XReferences> xReferences) {
        this.pathwayId = pathwayId;
        this.name = name;
        this.xReferences = xReferences;
    }

    @Id
    public String getPathwayId() {
        return this.pathwayId;
    }

    public void setPathwayId(String pathwayId) {
        this.pathwayId = pathwayId;
    }

    @Column
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.pathway", cascade = CascadeType.ALL)
    public Set<XReferences> getXReferences() {
        return this.xReferences;
    }

    public void setXReferences(Set<XReferences> xReferences) {
        this.xReferences = xReferences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pathway pathway = (Pathway) o;

        if (name != null ? !name.equals(pathway.name) : pathway.name != null) return false;
        if (pathwayId != null ? !pathwayId.equals(pathway.pathwayId) : pathway.pathwayId != null) return false;
        if (xReferences != null ? !xReferences.equals(pathway.xReferences) : pathway.xReferences != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pathwayId != null ? pathwayId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (xReferences != null ? xReferences.hashCode() : 0);
        return result;
    }
}
