package uk.ac.ebi.biomodels.tools.sbml;


import org.sbml.jsbml.*;
import uk.ac.ebi.biomodels.datastructure.sbml.Annotation;
import uk.ac.ebi.biomodels.datastructure.sbml.Bag;
import uk.ac.ebi.biomodels.datastructure.sbml.ModelElement;
import uk.ac.ebi.biomodels.enums.Namespace;

import javax.xml.stream.XMLStreamException;
import java.util.HashSet;
import java.util.Set;


/**
 * Extracts some model annotations (mainly from species) with the objective to use those in the reactome Analysis tool.
 *
 * @author Camille Laibe and Maximilian Koch
 * @version 20140704
 */
public class ExtractInformationFromSBMLModel {
    //private static final Logger logger = LoggerFactory.getLogger(ExtractAnnotationFromSBMLModel.class);

    public static Model getSBMLDModel(String sbmlModelAsString) {
        SBMLReader reader = new SBMLReader();
        SBMLDocument model = null;
        try {
            model = reader.readSBMLFromString(sbmlModelAsString);
        } catch (NullPointerException e) {
            //logger.error("Model is empty", e);
        } catch (XMLStreamException e) {
            //logger.error("Error on reading Model as XML", e);
        }
        return model != null ? model.getModel() : null;
    }

    /**
     * Extracts all the necessary annotations.
     * WARNING: currently only display the information on the console.
     */
    public static Set<Annotation> extractAnnotation(Model model) {
        Set<Annotation> annotationsOfSBML = new HashSet<Annotation>();
        for (Species species : model.getListOfSpecies()) {
            ModelElement component = extractComponentAnnotation(species);
            annotationsOfSBML.addAll(displayRelevantAnnotation(component));
        }
        return annotationsOfSBML;
    }

    /**
     * Extracts the taxonomical annotation from the model object.
     * Only considers "bqbiol:occursIn" or "bqbiol:hasTaxon" qualifiers.
     */
    public static String getModelTaxonomy(Model model) {
        String taxonomy = null;
        org.sbml.jsbml.Annotation modelAnnotation = model.getAnnotation();
        for (CVTerm cvTerm : modelAnnotation.getListOfCVTerms()) {
            String qualifier = getQualifier(cvTerm);
            // retrieves all the URIs
            for (String uri : cvTerm.getResources()) {
                if ((qualifier.equalsIgnoreCase("bqbiol:occursIn") || qualifier.equalsIgnoreCase("bqbiol:hasTaxon"))
                        && (uri.contains("taxonomy"))) {
                    taxonomy = extractIdFromURI(uri);
                }
            }
        }
        return taxonomy;
    }

    /**
     * Extract the necessary annotation from a model component.
     *
     * @param //species
     */
    private static ModelElement extractComponentAnnotation(SBase component) {
        ModelElement element = new ModelElement();
        for (CVTerm cvTerm : component.getCVTerms()) {
            Bag bag = new Bag();
            bag.setQualifier(getQualifier(cvTerm));
            // retrieves all the URIs
            for (String uri : cvTerm.getResources()) {
                Annotation annotation = new Annotation();
                annotation.setUri(uri);
                annotation.setEntityId(extractIdFromURI(uri));
                annotation.setNamespace(extractNamespaceFromURI(uri));
                bag.addAnnotation(annotation);
            }
            element.addBag(bag);
        }
        return element;
    }

    /**
     * Get the qualifier's name from a CVTerm.
     */
    private static String getQualifier(CVTerm cvTerm) {
        String qualifier;
        String namespacePrefix = cvTerm.isModelQualifier() ? "bqmodel" : "bqbiol";
        String qualifierName;
        if (cvTerm.isModelQualifier()) {
            qualifierName = cvTerm.getModelQualifierType().getElementNameEquivalent();
        } else {
            qualifierName = cvTerm.getBiologicalQualifierType().getElementNameEquivalent();
        }
        qualifier = namespacePrefix + ":" + qualifierName;

        return qualifier;
    }

    /**
     * Displays all annotations of a given sbml element which are relevant for the reactome data analyis tool.
     */
    private static Set<Annotation> displayRelevantAnnotation(ModelElement component) {
        Integer counterTmp;
        Set<Annotation> annotationsOfSBML = new HashSet<Annotation>();
        counterTmp = findAllAnnotationFromDataCollection(component, Namespace.UNIPROT.name());
        if (counterTmp > 0) {
            // some UniProt annotation found
            annotationsOfSBML.addAll(getAllAnnotationFromDataCollection(component, Namespace.UNIPROT.name()));
        } else {
            // no UniProt annotation found
            counterTmp = findAllAnnotationFromDataCollection(component, Namespace.CHEBI.name());
            if (counterTmp > 0) {
                // some ChEBI annotation found
                annotationsOfSBML.addAll(getAllAnnotationFromDataCollection(component, Namespace.CHEBI.name()));
            } else {
                // no ChEBI annotation found
                counterTmp = findAllAnnotationFromDataCollection(component, Namespace.CHEMBL_COMPOUND.name());
                if (counterTmp > 0) {
                    // some ChEMBL Compound annotation found
                    annotationsOfSBML.addAll(getAllAnnotationFromDataCollection(component, Namespace.CHEMBL_COMPOUND.name()));
                } else {
                    // no ChEMBL Compound annotation found
                    counterTmp = findAllAnnotationFromDataCollection(component, Namespace.ENSEMBL.name());
                    if (counterTmp > 0) {
                        // some Ensembl annotation found
                        annotationsOfSBML.addAll(getAllAnnotationFromDataCollection(component, Namespace.ENSEMBL.name()));
                    }
                }
            }
        }
        return annotationsOfSBML;
    }

    /**
     * Finds and counts all the annotation of a sbml component from a given data collection.
     */
    private static Integer findAllAnnotationFromDataCollection(ModelElement component, String namespace) {
        Integer counter = 0;
        if (component.getBags() != null) {
            for (Bag bag : component.getBags()) {
                for (Annotation annotation : bag.getAnnotations()) {
                    if (annotation.getNamespace().equalsIgnoreCase(namespace)) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    /**
     * Prints all the annotation of a sbml component from a given data collection.
     */
    private static Set<Annotation> getAllAnnotationFromDataCollection(ModelElement component, String namespace) {
        Set<Annotation> annotationsOfSBML = new HashSet<Annotation>();
        for (Bag bag : component.getBags()) {
            for (Annotation annotation : bag.getAnnotations()) {
                if (annotation.getNamespace().equalsIgnoreCase(namespace)) {
                    if (annotation.getEntityId().contains(":")) {
                        annotation.setEntityId(annotation.getEntityId().split(":")[1]);
                    }
                    annotationsOfSBML.add(annotation);
                }
            }
        }
        return annotationsOfSBML;
    }

    /**
     * Extracts the entity identifier from an Identifiers.org URI.
     * E.g. http://identifiers.org/taxonomy/8292   >>>  8292
     */
    private static String extractIdFromURI(String uri) {
        return uri.substring(uri.lastIndexOf("/") + 1);
    }

    /**
     * Extracts the namespace from an Identifiers.org URI.
     * E.g. http://identifiers.org/taxonomy/8292   >>>  taxonomy
     */
    private static String extractNamespaceFromURI(String uri) {
        return uri.substring(23, uri.indexOf("/", 24));
    }
}