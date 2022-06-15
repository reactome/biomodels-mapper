package org.reactome.server.models2pathways.biomodels.fetcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Jacksonized
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BioModelSearch {
    private List<Facet> facets;
    private String facetStats;
    private Integer matches;
    private List<ModelSummary> models;
}
