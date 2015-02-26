package org.reactome.server.core.model.sbml;

import org.reactome.server.core.enums.Species;
import org.reactome.server.core.utils.Annotation;

import java.util.Set;

/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */
public class SBMLModel {
    private String name;
    private String bioModelsID;
    private Species species;
    private Set<Annotation> sbmlModelAnnotations;

    /**
     * SBMLModel represents given information from the given
     * SBMLModel/XML and the BioModel-Webservice
     *
     * @param name
     * @param bioModelsID
     * @param species
     * @param sbmlModelAnnotations
     */
    public SBMLModel(String name, String bioModelsID, Species species,
                     Set<Annotation> sbmlModelAnnotations) {
        this.name = name;
        this.bioModelsID = bioModelsID;
        this.species = species;
        this.sbmlModelAnnotations = sbmlModelAnnotations;
    }

    public SBMLModel(String name, Species species, Set<Annotation> sbmlModelAnnotations) {
        this.name = name;
        this.species = species;
        this.sbmlModelAnnotations = sbmlModelAnnotations;
    }

    public String getName() {
        return name;
    }

    public String getBioModelsID() {
        return bioModelsID;
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
                ", bioModelsTaxonomyId='" + species + '\'' +
                ", sbmlModelAnnotations=" + sbmlModelAnnotations +
                '}';
    }
}
