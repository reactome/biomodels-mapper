package org.reactome.server.models2pathways.biomodels.fetcher.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Data
public class FacetValue {
    @JsonProperty("class")
    private String _class;
    private String count;
    private String label;
    private String value;

    public int getCount() {
        return Integer.parseInt(count);
    }
}
