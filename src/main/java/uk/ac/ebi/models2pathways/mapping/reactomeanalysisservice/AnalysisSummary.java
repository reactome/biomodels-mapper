package uk.ac.ebi.models2pathways.mapping.reactomeanalysisservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class AnalysisSummary {
    private String token;
    private String type;
    private String sampleName;
    private Long species;
    private boolean text = false;
    private String fileName;

    @JsonCreator
    public AnalysisSummary(
            @JsonProperty("token") String token,
            @JsonProperty("type") String type,
            @JsonProperty("sampleName") String sampleName,
            @JsonProperty("species") Long species,
            @JsonProperty("text") boolean text,
            @JsonProperty("fileName") String fileName) {
        this.token = token;
        this.type = type;
        this.sampleName = sampleName;
        this.species = species;
        this.text = text;
        this.fileName = fileName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public Long getSpecies() {
        return species;
    }

    public void setSpecies(Long species) {
        this.species = species;
    }

    public boolean isText() {
        return text;
    }

    public void setText(boolean text) {
        this.text = text;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}