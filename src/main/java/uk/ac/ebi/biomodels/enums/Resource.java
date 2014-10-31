package uk.ac.ebi.biomodels.enums;

/**
 * Created by Maximilian Koch on 16/10/2014.
 */
public enum Resource {
    UNIPROT("uniprot"),
    CHEBI("chebi"),
    CHEMBL_COMPOUND("chembl.compound"),
    ENSEMBL("ensembl");

    private final String namespace;

    private Resource(final String namespace) {
        this.namespace = namespace;
    }

    @Override
    public String toString() {
        return "Namespaces{" +
                "namespace='" + namespace + '\'' +
                '}';
    }
}
