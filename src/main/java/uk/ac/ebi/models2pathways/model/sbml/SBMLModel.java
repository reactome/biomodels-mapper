package uk.ac.ebi.models2pathways.model.sbml;

import uk.ac.ebi.models2pathways.enums.Species;
import uk.ac.ebi.models2pathways.helper.Annotation;

import java.util.Arrays;
import java.util.Set;

/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */
public class SBMLModel {
    private final String name;
    private final String bioModelsID;
    private final String[] authors;
    private final String publication;
    private final Species species;
    private final Set<Annotation> sbmlModelAnnotations;
    //private AnalysisResult reactomeAnalysisResult;

    /**
     * SBMLModel represents given information from the given
     * SBMLModel/XML and the BioModel-Webservice
     *
     * @param name
     * @param bioModelsID
     * @param authors
     * @param publication
     * @param species
     * @param sbmlModelAnnotations
     */
    public SBMLModel(String name, String bioModelsID, String[] authors, String publication, Species species,
                     Set<Annotation> sbmlModelAnnotations) {
        this.name = name;
        this.bioModelsID = bioModelsID;
        this.authors = authors;
        this.publication = publication;
        this.species = species;
        this.sbmlModelAnnotations = sbmlModelAnnotations;
    }

    public String getName() {
        return name;
    }

    public String getBioModelsID() {
        return bioModelsID;
    }

    public String[] getAuthors() {
        return authors;
    }

    public String getPublication() {
        return publication;
    }

    public Species getBioModelsTaxonomyId() {
        return species;
    }

    public Set<Annotation> getSBMLModelAnnotations() {
        return sbmlModelAnnotations;
    }

    @Override
    public String toString() {
        return "SBMLModel{" +
                "name='" + name + '\'' +
                ", bioModelsID='" + bioModelsID + '\'' +
                ", authors=" + Arrays.toString(authors) +
                ", publication='" + publication + '\'' +
                ", bioModelsTaxonomyId='" + species + '\'' +
                ", sbmlModelAnnotations=" + sbmlModelAnnotations +
                '}';
    }
}
