package org.reactome.server.core.models2pathways.core;

import org.reactome.server.analysis.core.model.HierarchiesData;
import org.reactome.server.analysis.core.model.SpeciesNode;
import org.reactome.server.analysis.core.model.UserData;
import org.reactome.server.core.models2pathways.biomodels.helper.AnnotationHelper;
import org.reactome.server.core.models2pathways.biomodels.model.SBMLModel;
import org.reactome.server.core.models2pathways.core.entrypoint.Models2Pathways;
import org.reactome.server.core.models2pathways.core.helper.SpeciesHelper;
import org.reactome.server.core.models2pathways.core.utils.FileExporter;
import org.reactome.server.core.models2pathways.reactome.helper.AnalysisCoreHelper;
import org.reactome.server.core.models2pathways.reactome.model.AnalysisResult;
import org.reactome.server.core.models2pathways.reactome.model.AnalysisStoredResult;
import org.reactome.server.core.models2pathways.reactome.model.PathwaySummary;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class Consumer implements Runnable {
    final static Logger logger = Logger.getLogger(Consumer.class.getName());

    private Double significantFDR;
    private Double extendedFDR;
    private Double reactionCoverage;
    private AnalysisCoreHelper analysisCoreHelper;
    private BlockingQueue<SBMLModel> sbmlModelBlockingQueue;

    public Consumer(BlockingQueue<SBMLModel> sbmlModelBlockingQueue, double significantFDR, double reactionCoverage) {
        this.sbmlModelBlockingQueue = sbmlModelBlockingQueue;
        this.significantFDR = significantFDR;
        this.reactionCoverage = reactionCoverage;
        this.analysisCoreHelper = new AnalysisCoreHelper();
    }

    public Consumer(BlockingQueue<SBMLModel> sbmlModelBlockingQueue, double significantFDR, double extendedFDR, String reactionCoverage) {
        this.sbmlModelBlockingQueue = sbmlModelBlockingQueue;
        this.significantFDR = significantFDR;
        this.extendedFDR = extendedFDR;
        this.reactionCoverage = Double.valueOf(reactionCoverage);
        this.analysisCoreHelper = new AnalysisCoreHelper();
    }

    @Override
    public void run() {
        while (Models2Pathways.isProducerAlive()) {
            SBMLModel sbmlModel;
            try {
                sbmlModel = sbmlModelBlockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                continue;
            }

            //Remove all trivial chemicals
            sbmlModel.getAnnotations().removeAll(AnnotationHelper.getAnnotationsWithTrivialChemicals());
            //On my way to get the AnalysisResult Object!!!
            UserData userData;
            SpeciesNode speciesNode;
            HierarchiesData hierarchiesData;
            AnalysisStoredResult analysisStoredResult;
            List<PathwaySummary> pathwaySummaryList;
            AnalysisResult analysisResult;
            try {
                userData = analysisCoreHelper.getUserData(sbmlModel.getName(), sbmlModel.getAnnotations());
                speciesNode = analysisCoreHelper.convertToSpeciesNode(SpeciesHelper.getInstance().getSpecieByBioMdSpecieId(sbmlModel.getBioMdId()));
                hierarchiesData = analysisCoreHelper.getHierarchiesData(userData, speciesNode);
                analysisStoredResult = new AnalysisStoredResult(userData, hierarchiesData);
                analysisStoredResult.setHitPathways(hierarchiesData.getUniqueHitPathways(speciesNode));
                pathwaySummaryList = analysisCoreHelper.getPathwaySummaryList(hierarchiesData.getUniqueHitPathways(speciesNode), "TOTAL");
                analysisResult = analysisCoreHelper.getAnalysisResult(analysisStoredResult, pathwaySummaryList);
            } catch (NullPointerException e) {
                e.printStackTrace();
                System.out.println("continue");
                continue;
            }
            //Check resource
            String resourceSummary = analysisCoreHelper.getResource(analysisResult.getResourceSummary());
            //If other resource 
            if (!resourceSummary.equals("TOTAL")) {
                pathwaySummaryList = analysisCoreHelper.getPathwaySummaryList(hierarchiesData.getUniqueHitPathways(speciesNode), resourceSummary);
                analysisResult = analysisCoreHelper.getAnalysisResult(analysisStoredResult, pathwaySummaryList);
            }
            //remove not significant pathways
            analysisResult.setReliablePathways(analysisCoreHelper.getReliablePathways(analysisResult.getPathways(), significantFDR, sbmlModel, reactionCoverage));
            if (analysisResult.getReliablePathways().isEmpty() && extendedFDR != null) {
                analysisResult.setReliablePathways(analysisCoreHelper.getReliablePathways(analysisResult.getPathways(), extendedFDR, sbmlModel, reactionCoverage));
            }
            for (PathwaySummary pathwaySummary : analysisResult.getReliablePathways()) {
                FileExporter.addRow(pathwaySummary, sbmlModel);
            }
            System.out.println(analysisResult.getReliablePathways().size());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.info("Consumer has finished.");
        FileExporter.closeFile();
        logger.info("\nProcess finished");
    }
}