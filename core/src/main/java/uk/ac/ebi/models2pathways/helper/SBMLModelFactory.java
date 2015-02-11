package uk.ac.ebi.models2pathways.helper;

import org.sbml.jsbml.Model;
import uk.ac.ebi.biomodels.ws.BioModelsWSClient;
import uk.ac.ebi.biomodels.ws.BioModelsWSException;
import uk.ac.ebi.models2pathways.enums.Species;
import uk.ac.ebi.models2pathways.model.sbml.SBMLModel;
import uk.ac.ebi.models2pathways.utils.Annotation;
import uk.ac.ebi.models2pathways.utils.ExtractInformationFromSBMLModel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */
public class SBMLModelFactory {
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
        return new String[0];
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
        return "";
    }

    private static String[] getSBMLModelAuthors(String modelId) {
        try {
            return client.getAuthorsByModelId(modelId);
        } catch (BioModelsWSException e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    private static String getSBMLModelPublication(String modelId) {
        try {
            return client.getPublicationByModelId(modelId);
        } catch (BioModelsWSException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getSBMLModelName(String modelId) {
        try {
            return client.getModelNameById(modelId);
        } catch (BioModelsWSException e) {
            e.printStackTrace();
        }
        return "";
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
    public static SBMLModel getSBMLModel(String modelId) {
        Model model = getSBMLXMLInformation(getModelSBMLByModelId(modelId));
        String sbmlModelName = getSBMLModelName(modelId);
        String[] sbmlModelAuthors = getSBMLModelAuthors(modelId);
        String sbmlModelPublications = getSBMLModelPublication(modelId);
        Species sbmlModelTaxonomy = getSBMLModelTaxonomy(model);
        Set<Annotation> sbmlModelAnnotations = getSBMLModelAnnotations(model);
        return new SBMLModel(sbmlModelName, modelId, sbmlModelAuthors,
                sbmlModelPublications, sbmlModelTaxonomy, sbmlModelAnnotations);
    }
}
