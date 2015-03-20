package org.reactome.server.core.models2pathways.biomodels.helper;

import org.reactome.server.core.models2pathways.biomodels.model.Annotation;
import org.reactome.server.core.models2pathways.biomodels.model.SBMLModel;
import org.reactome.server.core.models2pathways.core.helper.SpeciesHelper;
import org.reactome.server.core.models2pathways.core.model.Specie;
import org.sbml.jsbml.Model;
import uk.ac.ebi.biomodels.ws.BioModelsWSClient;
import uk.ac.ebi.biomodels.ws.BioModelsWSException;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */
public class SBMLModelFactory {
    final static Logger logger = Logger.getLogger(SBMLModelFactory.class.getName());

    private static final BioModelsWSClient client = new BioModelsWSClient();
    //todo ConnectException

    /**
     * Returns a collection of all sbml-Model-Names based on all taxonomy ids
     * which are given in Species enum
     *
     * @return
     */
    public static Set<Long> getAllBioMdIdsByAllTaxonomyIds() {
        Set<Long> allBioMDIds = new HashSet<>();
        for (Specie specie : SpeciesHelper.getInstance().getSpecies()) {
            Set<Long> bioMDIds = getModelsIdByTaxonomyId(specie.getBioMdId());
            allBioMDIds.addAll(bioMDIds);
        }
        return allBioMDIds;
    }

    /**
     * Returns an array of all sbml-Model-Names based on the given taxonomy id.
     *
     * @param taxonomyId
     * @return
     */
    private static Set<Long> getModelsIdByTaxonomyId(Long taxonomyId) {
        String[] bioMDIdsTemp = new String[0];
        try {
            bioMDIdsTemp = client.getModelsIdByTaxonomyId(taxonomyId.toString());
        } catch (BioModelsWSException e) {
            logger.info("Error on retrieving BioModels Id on taxonomy id. \n Please restart the process");
            e.printStackTrace();
            System.exit(1);
        }
        Set<Long> bioMDIds = new HashSet<>();
        for (String bioMDId : bioMDIdsTemp) {
            bioMDIds.add(Long.valueOf(bioMDId));
        }
        return bioMDIds;
    }

    /**
     * Returns the sbml-Model based on the given sbml-Model-Name
     *
     * @param bioMdId
     * @return
     */
    private static String getModelSBMLByModelId(Long bioMdId) {
        String bioMdSBML = null;
        try {
            bioMdSBML = client.getModelSBMLById(bioMdId.toString());
        } catch (BioModelsWSException e) {
            logger.info("Error on retrieving SBML-Files on " + bioMdId + ".\n Please restart the process");
            e.printStackTrace();
            System.exit(1);
        }
        if (bioMdSBML == null) {
            logger.info("Retrieved BioModel-SBML, for " + bioMdId + " is 'null' \n Please restart the process");
            System.exit(1);
        }
        return bioMdSBML;
    }

    /**
     * Getting information out of the SBML-Model/XML
     */
    private static Model getSBMLXMLInformation(String sbmlModelAsString) {
        return ExtractInformationFromSBMLModel.getSBMLDModel(sbmlModelAsString);
    }

    private static Specie getSBMLModelTaxonomy(Model model) {
        Long sbmlXMLInformation = Long.valueOf(ExtractInformationFromSBMLModel.getModelTaxonomy(model));
        return SpeciesHelper.getInstance().getSpecieByBioMdSpecieId(sbmlXMLInformation);
    }

    private static Set<Annotation> getSBMLModelAnnotations(Model model) {
        return ExtractInformationFromSBMLModel.extractAnnotation(model);
    }

    /**
     * Returns a SBML-Model object by given model id (e.g.:"BIOMD0000000464").
     */
    public static SBMLModel getSBMLModelByModelId(Long modelId) {
        Model model = getSBMLXMLInformation(getModelSBMLByModelId(modelId));
        String sbmlModelName = model.getName();
        Specie sbmlModelTaxonomy = getSBMLModelTaxonomy(model);
        Set<Annotation> sbmlModelAnnotations = getSBMLModelAnnotations(model);
        return new SBMLModel(sbmlModelName, modelId, sbmlModelTaxonomy, sbmlModelAnnotations);
    }
}
