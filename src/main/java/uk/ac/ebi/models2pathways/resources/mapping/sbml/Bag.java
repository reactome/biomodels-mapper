package uk.ac.ebi.models2pathways.resources.mapping.sbml;

import java.util.ArrayList;
import java.util.List;


/**
 * Stores the information in an rdf:bag inside an sbml file.
 *
 * @author Camille Laibe
 * @version 20140703
 */
public class Bag {
    private String qualifier;
    private List<Annotation> annotations;

    /**
     * Default constructor: builds an empty object.
     */
    public Bag() {
        this.qualifier = null;
        this.annotations = null;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Qualifier: ");
        stringBuilder.append(this.qualifier);
        stringBuilder.append("\nAnnotations:");
        for (Annotation annotation : this.annotations) {
            stringBuilder.append(annotation.toString());
        }
        return stringBuilder.toString();
    }

    public String getQualifier() {
        return this.qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public List<Annotation> getAnnotations() {
        return this.annotations;
    }

    public void setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
    }

    public void addAnnotation(Annotation annotation) {
        if (null == this.annotations) {
            this.annotations = new ArrayList<Annotation>();
        }
        this.annotations.add(annotation);
    }
}
