package uk.ac.ebi.biomodels.tools.sbml;

import uk.ac.ebi.biomodels.datastructure.sbml.Annotation;
import uk.ac.ebi.biomodels.enums.Species;

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
     * SBMLModel/XML and the BioModels-Webservice
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

    public Set<Annotation> getSbmlModelAnnotations() {
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
