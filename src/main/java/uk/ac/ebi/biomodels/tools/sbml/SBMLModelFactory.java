package uk.ac.ebi.biomodels.tools.sbml;

import org.sbml.jsbml.Model;
import uk.ac.ebi.biomodels.datastructure.sbml.Annotation;
import uk.ac.ebi.biomodels.enums.Species;
import uk.ac.ebi.biomodels.ws.BioModelsWSClient;
import uk.ac.ebi.biomodels.ws.BioModelsWSException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Maximilian Koch on 29/10/2014.
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
            allModelIdsByTaxonomies.addAll(Arrays.asList(getModelsIdByTaxonomyId(bioModelsTaxonomyId)));
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
        return null;
    }

    private static String[] getSBMLModelAuthors(String modelId) {
        try {
            return client.getAuthorsByModelId(modelId);
        } catch (BioModelsWSException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getSBMLModelPublication(String modelId) {
        try {
            return client.getPublicationByModelId(modelId);
        } catch (BioModelsWSException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getSBMLModelName(String modelId) {
        try {
            return client.getModelNameById(modelId);
        } catch (BioModelsWSException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * Getting information out of the SBML-Model/XML
     */
    private static Model getSBMLXMLInformation(String sbmlModelAsString) {
        return ExtractInformationFromSBMLModel.getSBMLDModel(sbmlModelAsString);
    }

    private static Species getSBMLModelTaxonomy(Model model) {
        return Species.getSpeciesByBioModelsTaxonomyid(ExtractInformationFromSBMLModel.getModelTaxonomy(model));
    }

    private static Set<Annotation> getSBMLModelAnnotations(Model model) {
        return ExtractInformationFromSBMLModel.extractAnnotation(model);
    }

    /**
     *
     *Returns a SBML-Model object.
     */
    public static SBMLModel getSBMLModel(String modelId) {
        Model model = getSBMLXMLInformation(getModelSBMLByModelId(modelId));
        return new SBMLModel(getSBMLModelName(modelId), modelId, getSBMLModelAuthors(modelId),
                getSBMLModelPublication(modelId), getSBMLModelTaxonomy(model), getSBMLModelAnnotations(model));
    }
}
