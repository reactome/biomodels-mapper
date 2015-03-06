package org.reactome.server.core.enums;

/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */
public enum Namespace {
    UNIPROT("uniprot"),
    CHEBI("chebi"),
    OBO_CHEBI("obo.chebi"),
    CHEMBL_COMPOUND("chembl.compound"),
    ENSEMBL("ensembl");

    private final String namespace;

    private Namespace(final String namespace) {
        this.namespace = namespace;
    }

    public String getNamespace() {
        return namespace;
    }

    @Override
    public String toString() {
        return "Namespaces{" +
                "namespace='" + namespace + '\'' +
                '}';
    }
}
//    UNIPROT("uniprot", new String[]{"uniprot"}),
//        CHEBI("chebi", new String[]{"chebi", "obo.chebi"}),
//        CHEMBL_COMPOUND("chembl.compound", new String[]{"compound.compound"}),
//        ENSEMBL("uniprot", new String[]{"ensembl"});
//
//private final String namespace;
//private final String [] namespaceAlternatives;
//
//        Namespace(String namespace, String[] namespaceAlternatives) {
//        this.namespace = namespace;
//        this.namespaceAlternatives = namespaceAlternatives;
//        }
//
//public String getNamespace() {
//        return namespace;
//        }
//
//public String[] getNamespaceAlternatives() {
//        return namespaceAlternatives;
//        }
//
//@Override
//public String toString() {
//        return "Namespaces{" +
//        "namespace='" + namespace + '\'' +
//        '}';
//        }