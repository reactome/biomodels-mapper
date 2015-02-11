package uk.ac.ebi.models2pathways.database;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class Models2PathwayDAO {

    private final JdbcTemplate jdbcTemplate;

    public Models2PathwayDAO() {
        BasicDataSource dataSource = DataSourceFactory.getDatabaseConnection();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        closeConnection();
    }
    
    public void createPathwaysTable() {
        String query = "CREATE TABLE Pathways (pathwayID VARCHAR(255) PRIMARY KEY, name VARCHAR(255))";
        jdbcTemplate.execute(query);
        closeConnection();
    }
    
    public void createBioModelsTable() {
        String query = "CREATE TABLE BioModels (biomodelID VARCHAR(255) PRIMARY KEY, name VARCHAR(255), authors VARCHAR(255))";
        jdbcTemplate.execute(query);
        closeConnection();
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
    }
    
    public void dropPathwaysTable() {
        String query = "DROP TABLE Pathways";
        try {
            jdbcTemplate.update(query);
        } catch (Exception ignored) {
        }
        closeConnection();
    }
    
    public void dropBioModelsTable() {
        String query = "DROP TABLE BioModels";
        try {
            jdbcTemplate.update(query);
        } catch (Exception ignored) {
        }
        closeConnection();
    }

    public void dropXReferencesTable() {
        String query = "DROP TABLE xReferences";
        try {
            jdbcTemplate.update(query);
        } catch (Exception ignored) {
        }
        closeConnection();
    }
    
    public void insertPathway(String pathwayID, String name) {
        String query = "INSERT INTO Pathways values (?,?);";
        jdbcTemplate.update(query, pathwayID, name);

    }

    public void insertBioModel(String biomodelID, String name, String authors) {
        String query = "INSERT INTO BioModels values (?,?,?);";
        jdbcTemplate.update(query, biomodelID, name, authors);
        closeConnection();
    }

    public void insertXReference(String pathwayID, String biomodelID, double pValue, double fdr, String resource,
                                 int reactionsTotal, int reactionsFound, int entitiesTotal, int entitiesFound, String species,
                                 boolean hasMinPValue, boolean hasApproval) {
        String query = "INSERT INTO xReferences values (?,?,?,?,?,?,?,?,?,?,?,?);";
        jdbcTemplate.update(query, pathwayID, biomodelID, pValue, fdr, resource, reactionsTotal, reactionsFound, entitiesTotal, entitiesFound, species, hasMinPValue, hasApproval);
        closeConnection();
    }

    private void closeConnection() {
        try {
            jdbcTemplate.getDataSource().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}