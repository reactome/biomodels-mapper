package org.reactome.server.models2pathways.reactome.helper;

import org.reactome.server.analysis.core.model.resource.MainResource;
import org.reactome.server.models2pathways.reactome.model.PathwayNodeSummary;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public enum AnalysisSortType {
    NAME(PathwayNodeSummary::getName, (s, r) -> s.getName()),
    TOTAL_ENTITIES(
            s -> s.getData() != null ? s.getData().getEntitiesCount() : null,
            (s, r) -> s.getData() != null ? s.getData().getEntitiesCount(r) : null
    ),
    TOTAL_REACTIONS(
            s -> s.getData() != null ? s.getData().getReactionsCount() : null,
            (s, r) -> s.getData() != null ? s.getData().getReactionsCount(r) : null
    ),
    FOUND_ENTITIES(
            s -> s.getData() != null ? s.getData().getEntitiesFound() : null,
            (s, r) -> s.getData() != null ? s.getData().getEntitiesFound(r) : null
    ),
    FOUND_REACTIONS(
            s -> s.getData() != null ? s.getData().getReactionsFound() : null,
            (s, r) -> s.getData() != null ? s.getData().getReactionsFound(r) : null
    ),
    ENTITIES_RATIO(
            s -> s.getData() != null ? s.getData().getEntitiesRatio() : null,
            (s, r) -> s.getData() != null ? s.getData().getEntitiesRatio(r) : null
    ),
    ENTITIES_PVALUE(
            s -> s.getData() != null ? s.getData().getEntitiesPValue() : null,
            (s, r) -> s.getData() != null ? s.getData().getEntitiesPValue(r) : null
    ),
    ENTITIES_FDR(
            s -> s.getData() != null ? s.getData().getEntitiesFDR() : null,
            (s, r) -> s.getData() != null ? s.getData().getEntitiesFDR(r) : null
    ),
    REACTIONS_RATIO(
            s -> s.getData() != null ? s.getData().getReactionsRatio() : null,
            (s, r) -> s.getData() != null ? s.getData().getReactionsRatio(r) : null
    );

    public final Function<PathwayNodeSummary, Comparable<?>> extractor;
    public final BiFunction<PathwayNodeSummary, MainResource, Comparable<?>> extractorWithResource;

    AnalysisSortType(Function<PathwayNodeSummary, Comparable<?>> extractor, BiFunction<PathwayNodeSummary, MainResource, Comparable<?>> extractorWithResource) {
        this.extractor = extractor;
        this.extractorWithResource = extractorWithResource;
    }

    public static AnalysisSortType getSortType(String type) {
        if (type != null) {
            for (AnalysisSortType sortType : values()) {
                if (sortType.toString().equals(type.toUpperCase())) {
                    return sortType;
                }
            }
        }
        return ENTITIES_PVALUE;
    }
}
