package org.reactome.server.models2pathways.biomodels.fetcher.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Jacksonized
@Builder
@Data
public class Facet {
    @JsonProperty("class")
    private String _class;
    private String id;
    private String label;
    private int total;
    private List<FacetValue> facetValues;
}
