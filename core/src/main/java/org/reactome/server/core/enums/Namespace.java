package org.reactome.server.core.enums;

/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */
public enum Namespace {
    UNIPROT("uniprot"),
    CHEBI("chebi"),
    CHEMBL_COMPOUND("chembl.compound"),
    ENSEMBL("ensembl");

    private final String namespace;

    private Namespace(final String namespace) {
        this.namespace = namespace;
    }

    @Override
    public String toString() {
        return "Namespaces{" +
                "namespace='" + namespace + '\'' +
                '}';
    }
}
