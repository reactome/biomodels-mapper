package org.reactome.server.core.database;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class Models2PathwayDAOImpl {
    final static Logger logger = Logger.getLogger(Models2PathwayDAOImpl.class.getName());

    private static JdbcTemplate jdbcTemplate;

    public static void createJDBCTemplate() {
        BasicDataSource dataSource = DataSourceFactory.getDatabaseConnection();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*********************************************************************************************
     *                                                                                           *
     *                              Database structure setup                                     *
     *                                                                                           *
     *********************************************************************************************/
    public static void createPathwaysTable() {
        String query = "CREATE TABLE Pathways (pathwayID VARCHAR(255) PRIMARY KEY, name VARCHAR(255))";
        jdbcTemplate.execute(query);
        closeConnection();
        
    }

    public static void createBioModelsTable() {
        String query = "CREATE TABLE BioModels (biomodelID VARCHAR(255) PRIMARY KEY, name VARCHAR(255))";
        jdbcTemplate.execute(query);
        closeConnection();
    }

    public static void createXReferencesTable() {
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
    }

    public static void dropPathwaysTable() {
        String query = "DROP TABLE Pathways";
        try {
            jdbcTemplate.update(query);
        } catch (Exception ignored) {
        }
        closeConnection();
    }

    public static void dropBioModelsTable() {
        String query = "DROP TABLE BioModels";
        try {
            jdbcTemplate.update(query);
        } catch (Exception ignored) {
        }
        closeConnection();
    }

    public static void dropXReferencesTable() {
        String query = "DROP TABLE xReferences";
        try {
            jdbcTemplate.update(query);
        } catch (Exception ignored) {
        }
        closeConnection();
    }

    /*********************************************************************************************
     *                                                                                           *
     *                              Creating of new database entries                             *
     *                                                                                           *
     *********************************************************************************************/

    public static void insertPathway(String pathwayID, String name) {
        String query = "INSERT INTO Pathways values (?,?)";
        try {
            jdbcTemplate.update(query, pathwayID, name);
        } catch (DuplicateKeyException ignore) {
        }
        closeConnection();
    }

    public static void insertBioModel(String biomodelID, String name) {
        String query = "INSERT INTO BioModels values (?,?)";
        try {
            jdbcTemplate.update(query, biomodelID, name);
        } catch (DuplicateKeyException ignore) {
        }
        closeConnection();
    }

    public static void insertXReference(String pathwayID, String biomodelID, double pValue, double fdr, String resource,
                                 int reactionsTotal, int reactionsFound, int entitiesTotal, int entitiesFound, String species,
                                 boolean hasMinPValue, boolean hasApproval) {
        String query = "INSERT INTO xReferences values (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            jdbcTemplate.update(query, pathwayID, biomodelID, pValue, fdr, resource, reactionsTotal, reactionsFound,
                    entitiesTotal, entitiesFound, species, hasMinPValue, hasApproval);
        } catch (DuplicateKeyException ignore) {
        }
        closeConnection();
    }

    private static void closeConnection() {
        try {
            jdbcTemplate.getDataSource().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}