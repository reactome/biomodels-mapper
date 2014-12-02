package uk.ac.ebi.models2pathways.tools;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import uk.ac.ebi.models2pathways.database.entitys.BioModel;
import uk.ac.ebi.models2pathways.database.entitys.Pathway;
import uk.ac.ebi.models2pathways.database.entitys.XReferences;
import uk.ac.ebi.models2pathways.resources.mapping.reactomeanalysisservice.PathwaySummary;
import uk.ac.ebi.models2pathways.tools.sbml.SBMLModel;

import java.util.Arrays;

/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */
public class DatabaseHandler {
    private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public static Pathway createPathway(PathwaySummary pathwaySummary) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Pathway pathway = new Pathway();
        pathway.setName(pathwaySummary.getName());
        pathway.setPathwayId(pathwaySummary.getStId());
        if (getPathway(pathway.getPathwayId()) != null) {
            session.close();
            return getPathway(pathway.getPathwayId());
        }
        session.save(pathway);
        transaction.commit();
        session.close();
        return pathway;
    }

    public static BioModel createBioModel(SBMLModel sbmlModel) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        BioModel bioModel = new BioModel(sbmlModel.getBioModelsID(), sbmlModel.getName(), Arrays.toString(sbmlModel.getAuthors()));
        session.save(bioModel);
        transaction.commit();
        session.close();
        return bioModel;
    }

    public static void createXReference(BioModel bioModel, Pathway pathway) {
        System.out.println(pathway.getPathwayId());
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        XReferences xReferences = new XReferences();
        xReferences.setBioModel(bioModel);
        xReferences.setPathways(pathway);
        session.save(xReferences);
        transaction.commit();
        session.close();
    }

    public static Pathway getPathway(String pathwayID) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Pathway pathway = (Pathway) session.get(Pathway.class, pathwayID);
        transaction.commit();
        session.close();
        return pathway;
    }

    public static BioModel getBioModel(String biomodelID) {
        Session session = sessionFactory.openSession();
        BioModel bioModel = (BioModel) session.get(BioModel.class, biomodelID);
        session.close();
        System.out.println(bioModel);
        return bioModel;
    }
}
