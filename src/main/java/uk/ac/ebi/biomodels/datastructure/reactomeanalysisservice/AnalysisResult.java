package uk.ac.ebi.biomodels.datastructure.reactomeanalysisservice;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
//@ApiModel(value = "AnalysisResult", description = "Contains general information about the result plus a list with the found pathways", discriminator = "", subTypes = {PathwaySummary.class})
public class AnalysisResult {
//    @ApiModelProperty(value = "Token associated with the query", notes = "Is a good practise to use it with the /token method for future filters of the result", required = true)

    private AnalysisSummary summary;

    private ExpressionSummary expression;

    //    @ApiModelProperty(value = "Number of identifiers in the sample without entities associated in the reactome database", required = true)
    private Integer identifiersNotFound;

    //TODO: Add found identifiers number

    //    @ApiModelProperty(value = "Number of pathways found with hits for the given sample", required = true)
    private Integer pathwaysFound;

    //    @ApiModelProperty(value = "List of pathways in which the input has been found", required = true) // dataType = "PathwaySummary" ) //, notes = "It may contain a subset of the pathways depending of the filtering options" )
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