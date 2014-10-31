package uk.ac.ebi.biomodels.datastructure.sbml;

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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Entity id: ");
        stringBuilder.append(entityId);
        stringBuilder.append("\nResource: ");
        stringBuilder.append(namespace);
        //str.append("\nURI: ");
        //str.append(uri);
        stringBuilder.append("\n");

        return stringBuilder.toString();
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
}
