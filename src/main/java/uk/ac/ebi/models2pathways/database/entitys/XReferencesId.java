package uk.ac.ebi.models2pathways.database.entitys;

/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class XReferencesId implements java.io.Serializable {

    private BioModel bioModel;
    private Pathway pathway;

    @ManyToOne
    public BioModel getBioModel() {
        return bioModel;
    }

    public void setBioModel(BioModel bioModel) {
        this.bioModel = bioModel;
    }

    @ManyToOne
    public Pathway getPathway() {
        return pathway;
    }

    public void setPathway(Pathway pathway) {
        this.pathway = pathway;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        XReferencesId that = (XReferencesId) o;

        if (bioModel != null ? !bioModel.equals(that.bioModel) : that.bioModel != null) return false;
        if (pathway != null ? !pathway.equals(that.pathway) : that.pathway != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (bioModel != null ? bioModel.hashCode() : 0);
        result = 31 * result + (pathway != null ? pathway.hashCode() : 0);
        return result;
    }

}