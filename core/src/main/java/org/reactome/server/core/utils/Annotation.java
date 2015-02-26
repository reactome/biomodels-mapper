package org.reactome.server.core.utils;

import java.util.Set;

/**
 * Stores one annotation (without any qualifier)
 *
 * @author Camille Laibe
 * @version 20140703
 */
public class Annotation {
    private String namespace;   // data collection namespace
    private String entityId;
    private String uri;   // full URI, as extracted from the sbml file

    /**
     * Default constructor: builds an empty object.
     */
    public Annotation() {
        this.namespace = null;
        this.entityId = null;
        this.uri = null;
    }

    public static String annotationsToAnalysisFormat(String model, Set<Annotation> annotations) {
        StringBuilder annotationsInAnalysisFormat = new StringBuilder();
        //Adding the name of the model to the sample data for a better identification in the PathwayBrowser result
        annotationsInAnalysisFormat.append("#").append(model).append(System.getProperty("line.separator"));
        for (Annotation annotation : annotations) {
            annotationsInAnalysisFormat.append(annotation.getEntityId()).append(System.getProperty("line.separator"));
        }
        return String.valueOf(annotationsInAnalysisFormat);
    }

    public String getNamespace() {
        return this.namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Annotation that = (Annotation) o;

        if (entityId != null ? !entityId.equals(that.entityId) : that.entityId != null) return false;
        if (namespace != null ? !namespace.equals(that.namespace) : that.namespace != null) return false;
        if (uri != null ? !uri.equals(that.uri) : that.uri != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = namespace != null ? namespace.hashCode() : 0;
        result = 31 * result + (entityId != null ? entityId.hashCode() : 0);
        result = 31 * result + (uri != null ? uri.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Annotation{" +
                "namespace='" + namespace + '\'' +
                ", entityId='" + entityId + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }
}
