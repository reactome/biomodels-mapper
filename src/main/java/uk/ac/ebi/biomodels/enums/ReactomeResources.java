package uk.ac.ebi.biomodels.enums;

/**
 * Created by Maximilian Koch on 30/10/2014.
 */
public enum ReactomeResources {
    TOTAL("TOTAL"),
    UNIPROT("UNIPROT"),
    ENSEMBL("ENSEMBL"),
    CHEBI("CHEBI"),
    NCBI_PROTEIN("NCBI_PROTEIN"),
    EMBL("EMBL"),
    COMPOUND("COMPOUND");

    private String reactomeResources;

    private ReactomeResources(String reactomeResources) {
        this.reactomeResources = reactomeResources;
    }

    public String getReactomeResources() {
        return reactomeResources;
    }
}
