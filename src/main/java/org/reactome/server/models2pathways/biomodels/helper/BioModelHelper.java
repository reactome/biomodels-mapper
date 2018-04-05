package org.reactome.server.models2pathways.biomodels.helper;

import org.apache.commons.io.FilenameUtils;
import org.reactome.server.models2pathways.biomodels.model.Annotation;
import org.reactome.server.models2pathways.biomodels.model.BioModel;
import org.reactome.server.models2pathways.core.model.Specie;
import org.sbml.jsbml.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Set;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class BioModelHelper {
    private static Logger logger = LoggerFactory.getLogger("m2pLogger");

    public static BioModel getBioModelByBioModelId(File bioMdFile) {
        Model model = ExtractInformationFromSBMLModel.convertBioModelSBMLString(bioMdFile);
        Specie specie = getBioModelTaxonomy(model);
        Set<Annotation> annotations = getBioModelAnnotations(model);
        String bioModelId = FilenameUtils.removeExtension(bioMdFile.getName());
        return new BioModel(model.getName(), bioModelId, specie, annotations);
    }

    private static Specie getBioModelTaxonomy(Model model) {
        return ExtractInformationFromSBMLModel.getModelTaxonomy(model);
    }

    private static Set<Annotation> getBioModelAnnotations(Model model) {
        return ExtractInformationFromSBMLModel.extractAnnotation(model);
    }


}
