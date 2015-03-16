package org.reactome.server.core.helper;

import org.reactome.server.analysis.core.components.EnrichmentAnalysis;
import org.reactome.server.analysis.core.data.AnalysisData;
import org.reactome.server.analysis.core.model.*;
import org.reactome.server.analysis.core.model.resource.MainResource;
import org.reactome.server.analysis.core.util.InputUtils;
import org.reactome.server.core.enums.Species;
import org.reactome.server.core.utils.Annotation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class AnalysisCoreHelper {
    private static EnrichmentAnalysis enrichmentAnalysis;

    public static void createEnrichmentAnalysis() throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        String structure = "/Users/maximiliankoch/models2pathways/analysis_v51.bin";
        AnalysisData analysisData = context.getBean(AnalysisData.class);
        analysisData.setFileName(structure);
        enrichmentAnalysis = context.getBean(EnrichmentAnalysis.class);
    }


    public SpeciesNode getSpeciesNode(Species species) {
//        species specie = Species.getSpeciesByBioModelsTaxonomyid(species.getReactomeTaxonomyId());
        return SpeciesNodeFactory.getSpeciesNode(Long.valueOf(species.getReactomeTaxonomyId()), species.name());
    }

    public List<PathwayNodeData> getPathwayNodeData(Set<Annotation> annotationSet, Species species) throws IOException {
        List<PathwayNodeData> pathwayNodeData = new ArrayList<>();
        String input = "/Users/maximiliankoch/models2pathways/myInputTest.txt";
        UserData ud = InputUtils.getUserData(new FileInputStream(input));
        SpeciesNode speciesNode = getSpeciesNode(species);
        HierarchiesData hierarchiesData = enrichmentAnalysis.overRepresentation(ud.getIdentifiers(), speciesNode);
        if (hierarchiesData != null) {
            for (PathwayNode node : hierarchiesData.getUniqueHitPathways(speciesNode)) {
                if (node != null) {
                    System.out.println(node);
                    pathwayNodeData.add(node.getPathwayNodeData());
                }
            }
        }
        return pathwayNodeData;
    }

    private void print(PathwayNode node) {
        String name = node.getName();
        PathwayNodeData data = node.getPathwayNodeData();

        for (MainResource resource : data.getResources()) {
            Integer found = data.getEntitiesFound(resource);
            if (found == 0) continue;
            Integer total = data.getEntitiesCount(resource);
            System.out.print(node.getSpecies().getName() + " >> " + resource.getName() + " >> " + name + " (" + found + "/" + total + ")");
            Double pValue = data.getEntitiesPValue(resource);
            Double ratio = data.getEntitiesRatio(resource);
            Double fdr = data.getEntitiesFDR(resource);
            if (pValue != null && ratio != null && fdr != null) {
                System.out.print("\t" + ratio + "\t" + pValue + "\t" + fdr);
//                DecimalFormat f = new DecimalFormat("#.####");
//                System.out.print("\t" + f.format(ratio) + "\t" + f.format(pValue));
            }

            System.out.print("\t|\t");
            found = data.getReactionsFound(resource);
            total = data.getReactionsCount(resource);
            System.out.println("[" + found + "/" + total + "]");
        }
    }
}