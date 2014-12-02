package uk.ac.ebi.models2pathways.database.entitys;

/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "BioModel")
public class BioModel implements java.io.Serializable {

    private String bioModelId;
    private String name;
    private String authors;
    //private Species species;
    private Set<XReferences> xReferences = new HashSet<XReferences>(0);

    public BioModel() {}

    public BioModel(String bioModelId, String name, String authors) {
        this.bioModelId = bioModelId;
        this.name = name;
        this.authors = authors;
    }

    public BioModel(String bioModelId, String name, String authors, Set<XReferences> xReferences) {
        this.bioModelId = bioModelId;
        this.name = name;
        this.authors = authors;
        this.xReferences = xReferences;
    }

    @Id
    @Column
    public String getBioModelId() {
        return this.bioModelId;
    }

    public void setBioModelId(String stockId) {
        this.bioModelId = stockId;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.bioModel", cascade = CascadeType.ALL)
    public Set<XReferences> getXReferences() {
        return this.xReferences;
    }

    public void setXReferences(Set<XReferences> xReferences) {
        this.xReferences = xReferences;
    }

    @Override
    public String toString() {
        return "BioModel{" +
                "bioModelId='" + bioModelId + '\'' +
                ", name='" + name + '\'' +
                ", authors='" + authors + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BioModel bioModel = (BioModel) o;

        if (authors != null ? !authors.equals(bioModel.authors) : bioModel.authors != null) return false;
        if (bioModelId != null ? !bioModelId.equals(bioModel.bioModelId) : bioModel.bioModelId != null) return false;
        if (name != null ? !name.equals(bioModel.name) : bioModel.name != null) return false;
        if (xReferences != null ? !xReferences.equals(bioModel.xReferences) : bioModel.xReferences != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bioModelId != null ? bioModelId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        result = 31 * result + (xReferences != null ? xReferences.hashCode() : 0);
        return result;
    }
}