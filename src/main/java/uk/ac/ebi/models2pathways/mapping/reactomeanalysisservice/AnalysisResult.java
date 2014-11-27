package uk.ac.ebi.models2pathways.mapping.reactomeanalysisservice;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class AnalysisResult {

    private AnalysisSummary summary;

    private ExpressionSummary expression;

    private Integer identifiersNotFound;

    //TODO: Add found identifiers number

    private Integer pathwaysFound;

    private List<PathwaySummary> pathways;

    private List<ResourceSummary> resourceSummary;

    @JsonCreator
    public AnalysisResult(
            @JsonProperty("summary") AnalysisSummary summary,
            @JsonProperty("expression") ExpressionSummary expression,
            @JsonProperty("identifiersNotFound") Integer identifiersNotFound,
            @JsonProperty("pathwaysFound") Integer pathwaysFound,
            @JsonProperty("pathways") List<PathwaySummary> pathways,
            @JsonProperty("resourceSummary") List<ResourceSummary> resourceSummary) {
        this.summary = summary;
        this.expression = expression;
        this.identifiersNotFound = identifiersNotFound;
        this.pathwaysFound = pathwaysFound;
        this.pathways = pathways;
        this.resourceSummary = resourceSummary;
    }

    public AnalysisSummary getSummary() {
        return summary;
    }

    public void setSummary(AnalysisSummary summary) {
        this.summary = summary;
    }

    public ExpressionSummary getExpression() {
        return expression;
    }

    public void setExpression(ExpressionSummary expression) {
        this.expression = expression;
    }

    public Integer getIdentifiersNotFound() {
        return identifiersNotFound;
    }

    public void setIdentifiersNotFound(Integer identifiersNotFound) {
        this.identifiersNotFound = identifiersNotFound;
    }

    public Integer getPathwaysFound() {
        return pathwaysFound;
    }

    public void setPathwaysFound(Integer pathwaysFound) {
        this.pathwaysFound = pathwaysFound;
    }

    public List<PathwaySummary> getPathways() {
        return pathways;
    }

    public void setPathways(List<PathwaySummary> pathways) {
        this.pathways = pathways;
    }

    public List<ResourceSummary> getResourceSummary() {
        return resourceSummary;
    }

    public void setResourceSummary(List<ResourceSummary> resourceSummary) {
        this.resourceSummary = resourceSummary;
    }
}