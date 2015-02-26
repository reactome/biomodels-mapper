package org.reactome.server.core.helper;

import org.reactome.server.core.enums.Species;
import org.reactome.server.core.model.sbml.SBMLModel;
import org.reactome.server.core.utils.Annotation;
import org.reactome.server.core.utils.ExtractInformationFromSBMLModel;
import org.sbml.jsbml.Model;
import uk.ac.ebi.biomodels.ws.BioModelsWSClient;
import uk.ac.ebi.biomodels.ws.BioModelsWSException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */
public class SBMLModelFactory {
    final static Logger logger = Logger.getLogger(Producer.class.getName());

    private static final BioModelsWSClient client = new BioModelsWSClient();

    /**
     * Returns a collection of all sbml-Model-Names based on all taxonomy ids
     * which are given in Species enum
     *
     * @return
     */
    public static Set<String> getAllModelIdsByAllTaxonomyIds() {
        Set<String> allModelIdsByTaxonomies = new HashSet<String>();
        for (String bioModelsTaxonomyId : Species.getAllBioModelsTaxonomyIds()) {
            String[] modelIDs = getModelsIdByTaxonomyId(bioModelsTaxonomyId);
            allModelIdsByTaxonomies.addAll(Arrays.asList(modelIDs));
        }
        return allModelIdsByTaxonomies;
    }

    /**
     * Returns an array of all sbml-Model-Names based on the given taxonomy id.
     *
     * @param bioModelsTaxonomyId
     * @return
     */
    private static String[] getModelsIdByTaxonomyId(String bioModelsTaxonomyId) {
        try {
            return client.getModelsIdByTaxonomyId(bioModelsTaxonomyId);
        } catch (BioModelsWSException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the sbml-Model based on the given sbml-Model-Name
     *
     * @param modelId
     * @return
     */
    private static String getModelSBMLByModelId(String modelId) {
        try {
            return client.getModelSBMLById(modelId);
        } catch (BioModelsWSException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Getting information out of the SBML-Model/XML
     */
    private static Model getSBMLXMLInformation(String sbmlModelAsString) {
        return ExtractInformationFromSBMLModel.getSBMLDModel(sbmlModelAsString);
    }

    private static Species getSBMLModelTaxonomy(Model model) {
        String sbmlXMLInformation = ExtractInformationFromSBMLModel.getModelTaxonomy(model);
        return Species.getSpeciesByBioModelsTaxonomyid(sbmlXMLInformation);
    }

    private static Set<Annotation> getSBMLModelAnnotations(Model model) {
        return ExtractInformationFromSBMLModel.extractAnnotation(model);
    }

    /**
     * Returns a SBML-Model object by given model id (e.g.:"BIOMD0000000464").
     */
    public static SBMLModel getSBMLModelByModelId(String modelId) {
        Model model = getSBMLXMLInformation(getModelSBMLByModelId(modelId));
        String sbmlModelName = model.getName();
        Species sbmlModelTaxonomy = getSBMLModelTaxonomy(model);
        Set<Annotation> sbmlModelAnnotations = getSBMLModelAnnotations(model);
        return new SBMLModel(sbmlModelName, modelId, sbmlModelTaxonomy, sbmlModelAnnotations);
    }
}
