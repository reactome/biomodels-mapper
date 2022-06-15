package org.reactome.server.models2pathways.biomodels.fetcher.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Data
public class ModelSummary {
    private String format;
    private String id;
    private String lastModified;
    private String name;
    private String submissionDate;
    private String submitter;
    private String url;
}
