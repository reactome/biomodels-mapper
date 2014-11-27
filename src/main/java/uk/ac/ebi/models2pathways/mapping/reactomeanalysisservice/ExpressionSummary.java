package uk.ac.ebi.models2pathways.mapping.reactomeanalysisservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class ExpressionSummary {
    List<String> columnNames;
    Double min;
    Double max;

    @JsonCreator
    public ExpressionSummary(
            @JsonProperty("columnNames") List<String> columnNames,
            @JsonProperty("min") Double min,
            @JsonProperty("max") Double max) {
        this.columnNames = columnNames;
        this.min = min;
        this.max = max;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }
}