package uk.ac.ebi.models2pathways.database.entitys;

/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */

import javax.persistence.*;

@Entity
@Table
@AssociationOverrides({
        @AssociationOverride(name = "pk.pathway",
                joinColumns = @JoinColumn(name = "PATHWAY_ID")),
        @AssociationOverride(name = "pk.bioModel",
                joinColumns = @JoinColumn(name = "BIOMODEL_ID"))})
public class XReferences implements java.io.Serializable {

    private XReferencesId pk = new XReferencesId();

    public XReferences() {}

    @EmbeddedId
    public XReferencesId getPk() {
        return pk;
    }

    public void setPk(XReferencesId pk) {
        this.pk = pk;
    }

    @Transient
    public BioModel getBioModel() {
        return getPk().getBioModel();
    }

    public void setBioModel(BioModel bioModel) {
        getPk().setBioModel(bioModel);
    }

    @Transient
    public Pathway getPathways() {
        return getPk().getPathway();
    }

    public void setPathways(Pathway pathway) {
        getPk().setPathway(pathway);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        XReferences that = (XReferences) o;

        if (pk != null ? !pk.equals(that.pk) : that.pk != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return pk != null ? pk.hashCode() : 0;
    }
}