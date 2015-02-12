package org.reactome.server.core.database;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.logging.Logger;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class Models2PathwayDAO {
    final static Logger logger = Logger.getLogger(Models2PathwayDAO.class.getName());

    private final JdbcTemplate jdbcTemplate;

    public Models2PathwayDAO() {
        BasicDataSource dataSource = DataSourceFactory.getDatabaseConnection();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*********************************************************************************************
     *                                                                                           *
     *                              Database structure setup                                     *
     *                                                                                           *
     *********************************************************************************************/
    public void createPathwaysTable() {
        String query = "CREATE TABLE Pathways (pathwayID VARCHAR(255) PRIMARY KEY, name VARCHAR(255))";
        jdbcTemplate.execute(query);
        closeConnection();
        logger.info("Pathways table created");
    }

    public void createBioModelsTable() {
        String query = "CREATE TABLE BioModels (biomodelID VARCHAR(255) PRIMARY KEY, name VARCHAR(255), authors VARCHAR(255))";
        jdbcTemplate.execute(query);
        closeConnection();
        logger.info("BioModels table created");
    }

    public void createXReferencesTable() {
        String query = "CREATE TABLE xReferences (pathwayID VARCHAR(255) NOT NULL, " +
                "biomodelID VARCHAR(255) NOT NULL, " +
                "pValue DOUBLE, " +
                "fdr DOUBLE, " +
                "resource VARCHAR(255), " +
                "reactionsTotal INTEGER, " +
                "reactionsFound INTEGER, " +
                "entitiesTotal INTEGER, " +
                "entitiesFound INTEGER, " +
                "species VARCHAR(255), " +
                "hasMinPValue BOOLEAN, " +
                "hasApproval BOOLEAN, " +
                "CONSTRAINT xReferencesID PRIMARY KEY (pathwayID, biomodelID), " +
                "FOREIGN KEY (pathwayID) REFERENCES Pathways (pathwayID), " +
                "FOREIGN KEY (biomodelID) REFERENCES BioModels (biomodelID))";
        jdbcTemplate.execute(query);
        closeConnection();
        logger.info("xReferences table created");
    }

    public void dropPathwaysTable() {
        String query = "DROP TABLE Pathways";
        try {
            jdbcTemplate.update(query);
        } catch (Exception ignored) {
        }
        closeConnection();
        logger.info("Pathways table deleted");
    }

    public void dropBioModelsTable() {
        String query = "DROP TABLE BioModels";
        try {
            jdbcTemplate.update(query);
        } catch (Exception ignored) {
        }
        closeConnection();
        logger.info("BioModels table deleted");
    }

    public void dropXReferencesTable() {
        String query = "DROP TABLE xReferences";
        try {
            jdbcTemplate.update(query);
        } catch (Exception ignored) {
        }
        closeConnection();
        logger.info("xReferences table deleted");
    }

    /*********************************************************************************************
     *                                                                                           *
     *                              Creating of new database entries                             *
     *                                                                                           *
     *********************************************************************************************/

    public void insertPathway(String pathwayID, String name) {
        String query = "INSERT INTO Pathways values (?,?);";
        try {
            jdbcTemplate.update(query, pathwayID, name);
        } catch (DuplicateKeyException ignore) {
        }
        closeConnection();
    }

    public void insertBioModel(String biomodelID, String name, String authors) {
        String query = "INSERT INTO BioModels values (?,?,?);";
        try {
            jdbcTemplate.update(query, biomodelID, name, authors);
        } catch (DuplicateKeyException ignore) {
        }
        closeConnection();
    }

    public void insertXReference(String pathwayID, String biomodelID, double pValue, double fdr, String resource,
                                 int reactionsTotal, int reactionsFound, int entitiesTotal, int entitiesFound, String species,
                                 boolean hasMinPValue, boolean hasApproval) {
        String query = "INSERT INTO xReferences values (?,?,?,?,?,?,?,?,?,?,?,?);";
        try {
            jdbcTemplate.update(query, pathwayID, biomodelID, pValue, fdr, resource, reactionsTotal, reactionsFound,
                    entitiesTotal, entitiesFound, species, hasMinPValue, hasApproval);
        } catch (DuplicateKeyException ignore) {
        }
        closeConnection();
    }

    private void closeConnection() {
//        try {
//            jdbcTemplate.getDataSource().getConnection().close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}