package org.reactome.server.models2pathways.reactome.helper;

import org.reactome.server.analysis.core.model.PathwayNodeData;
import org.reactome.server.analysis.core.model.resource.MainResource;
import org.reactome.server.models2pathways.reactome.model.PathwayNodeSummary;

import java.util.Comparator;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public abstract class ComparatorFactory {

    public static Comparator<PathwayNodeSummary> getComparator(AnalysisSortType type) {
        AnalysisSortType finalType = type != null ? type : AnalysisSortType.ENTITIES_PVALUE;
        return (o1, o2) -> {
            int rtn = compareTo(finalType.extractor.apply(o1), finalType.extractor.apply(o2));
            if (rtn == 0) {
                return genericCompare(o1, o2);
            }
            return rtn;
        };
    }

    public static Comparator<PathwayNodeSummary> getComparator(AnalysisSortType type, final MainResource r) {
        if (r == null) {
            return ComparatorFactory.getComparator(type);
        }
        AnalysisSortType finalType = type != null ? type : AnalysisSortType.ENTITIES_PVALUE;
        return (o1, o2) -> {
            int rtn = compareTo(finalType.extractorWithResource.apply(o1, r), finalType.extractorWithResource.apply(o2, r));
            if (rtn == 0) {
                return genericCompare(o1, o2, r);
            }
            return rtn;
        };
    }

    static int compareTo(Comparable c1, Comparable c2) {
        if (c1 == null) {
            return c2 == null ? 0 : 1;
        }
        return c2 == null ? -1 : c1.compareTo(c2);
    }

    static int genericCompare(PathwayNodeSummary o1, PathwayNodeSummary o2) {
        int rtn = getReactionsPercentage(o2).compareTo(getReactionsPercentage(o1));
        if (rtn == 0) {
            rtn = getEntitiesPercentage(o2).compareTo(getEntitiesPercentage(o1));
            if (rtn == 0) {
                rtn = o2.getData().getEntitiesCount().compareTo(o1.getData().getEntitiesCount());
                if (rtn == 0) {
                    rtn = o2.getData().getReactionsCount().compareTo(o1.getData().getReactionsCount());
                }
            }
        }
        return rtn;
    }

    static int genericCompare(PathwayNodeSummary o1, PathwayNodeSummary o2, MainResource r) {
        int rtn = getReactionsPercentage(o2, r).compareTo(getReactionsPercentage(o1, r));
        if (rtn == 0) {
            rtn = getEntitiesPercentage(o2, r).compareTo(getEntitiesPercentage(o1, r));
            if (rtn == 0) {
                rtn = o2.getData().getEntitiesCount(r).compareTo(o1.getData().getEntitiesCount(r));
                if (rtn == 0) {
                    rtn = o2.getData().getReactionsCount(r).compareTo(o1.getData().getReactionsCount(r));
                }
            }
        }
        return rtn;
    }

    static Double getEntitiesPercentage(PathwayNodeSummary node) {
        return node.getData().getEntitiesFound() / node.getData().getEntitiesCount().doubleValue();
    }

    static Double getEntitiesPercentage(PathwayNodeSummary node, MainResource r) {
        return node.getData().getEntitiesFound(r) / node.getData().getEntitiesCount(r).doubleValue();
    }

    static Double getReactionsPercentage(PathwayNodeSummary node) {
        return node.getData().getReactionsFound() / node.getData().getReactionsCount().doubleValue();
    }

    static Double getReactionsPercentage(PathwayNodeSummary node, MainResource r) {
        return node.getData().getReactionsFound(r) / node.getData().getReactionsCount(r).doubleValue();
    }
}
