package uk.ac.ebi.models2pathways.database.entities;

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
    private Double pValue;
    private Double fdr;
    private String resource;
    private Integer reactionsTotal;
    private Integer reactionsFound;
    private Integer entitiesTotal;
    private Integer entitiesFound;
    private String species;



    public XReferences() {}

    public XReferences(Double pValue, Double fdr, String resource, Integer reactionsTotal, Integer reactionsFound,
                       Integer entitiesTotal, Integer entitiesFound, String species) {
        this.pValue = pValue;
        this.fdr = fdr;
        this.resource = resource;
        this.reactionsTotal = reactionsTotal;
        this.reactionsFound = reactionsFound;
        this.entitiesTotal = entitiesTotal;
        this.entitiesFound = entitiesFound;
        this.species = species;
    }

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

    @Column
    public Double getpValue() {
        return pValue;
    }

    public void setpValue(Double pValue) {
        this.pValue = pValue;
    }

    @Column
    public Double getFdr() {
        return fdr;
    }

    public void setFdr(Double fdr) {
        this.fdr = fdr;
    }

    @Column
    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Column
    public Integer getReactionsTotal() {
        return reactionsTotal;
    }

    public void setReactionsTotal(Integer reactionsTotal) {
        this.reactionsTotal = reactionsTotal;
    }

    @Column
    public Integer getReactionsFound() {
        return reactionsFound;
    }

    public void setReactionsFound(Integer reactionsFound) {
        this.reactionsFound = reactionsFound;
    }

    @Column
    public Integer getEntitiesTotal() {
        return entitiesTotal;
    }

    public void setEntitiesTotal(Integer entitiesTotal) {
        this.entitiesTotal = entitiesTotal;
    }

    @Column
    public Integer getEntitiesFound() {
        return entitiesFound;
    }

    public void setEntitiesFound(Integer entitiesFound) {
        this.entitiesFound = entitiesFound;
    }

    @Column
    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        XReferences that = (XReferences) o;

        if (entitiesFound != null ? !entitiesFound.equals(that.entitiesFound) : that.entitiesFound != null)
            return false;
        if (entitiesTotal != null ? !entitiesTotal.equals(that.entitiesTotal) : that.entitiesTotal != null)
            return false;
        if (fdr != null ? !fdr.equals(that.fdr) : that.fdr != null) return false;
        if (pValue != null ? !pValue.equals(that.pValue) : that.pValue != null) return false;
        if (pk != null ? !pk.equals(that.pk) : that.pk != null) return false;
        if (reactionsFound != null ? !reactionsFound.equals(that.reactionsFound) : that.reactionsFound != null)
            return false;
        if (reactionsTotal != null ? !reactionsTotal.equals(that.reactionsTotal) : that.reactionsTotal != null)
            return false;
        if (resource != null ? !resource.equals(that.resource) : that.resource != null) return false;
        if (species != null ? !species.equals(that.species) : that.species != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pk != null ? pk.hashCode() : 0;
        result = 31 * result + (pValue != null ? pValue.hashCode() : 0);
        result = 31 * result + (fdr != null ? fdr.hashCode() : 0);
        result = 31 * result + (resource != null ? resource.hashCode() : 0);
        result = 31 * result + (reactionsTotal != null ? reactionsTotal.hashCode() : 0);
        result = 31 * result + (reactionsFound != null ? reactionsFound.hashCode() : 0);
        result = 31 * result + (entitiesTotal != null ? entitiesTotal.hashCode() : 0);
        result = 31 * result + (entitiesFound != null ? entitiesFound.hashCode() : 0);
        result = 31 * result + (species != null ? species.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "XReferences{" +
                "pk=" + pk +
                ", pValue=" + pValue +
                ", fdr=" + fdr +
                ", resource='" + resource + '\'' +
                ", reactionsTotal=" + reactionsTotal +
                ", reactionsFound=" + reactionsFound +
                ", entitiesTotal=" + entitiesTotal +
                ", entitiesFound=" + entitiesFound +
                ", species='" + species + '\'' +
                '}';
    }
}