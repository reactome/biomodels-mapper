package uk.ac.ebi.models2pathways.resources.mapping.sbml;

import java.util.ArrayList;
import java.util.List;


/**
 * Stores all the annotations related to one given model element.
 *
 * @author Camille Laibe
 * @version 20140703
 */
public class ModelElement {
    private List<Bag> bags;
    private String metaId;

    /**
     * Default constructor: builds an empty object.
     */
    public ModelElement() {
        this.metaId = null;
        this.bags = null;
    }

    public List<Bag> getBags() {
        return this.bags;
    }

    public void setBags(List<Bag> bags) {
        this.bags = bags;
    }

    public void addBag(Bag bag) {
        if (null == this.bags) {
            this.bags = new ArrayList<Bag>();
        }
        this.bags.add(bag);
    }

    public String getMetaId() {
        return this.metaId;
    }

    public void setMetaId(String metaId) {
        this.metaId = metaId;
    }
}