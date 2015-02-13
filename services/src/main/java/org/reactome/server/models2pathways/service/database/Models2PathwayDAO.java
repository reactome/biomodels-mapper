package org.reactome.server.models2pathways.service.database;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */

public class Models2PathwayDAO {
    private final JdbcTemplate jdbcTemplate;

    public Models2PathwayDAO() {
        BasicDataSource dataSource = DataSourceFactory.getDatabaseConnection();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Set<String> getAllBioModelsIdentifier(String pathWayIdentifier) {
        Set<String> bioModelsIdentifiers = new HashSet<>();
        String query = "SELECT BioModels.biomodelID FROM BioModels " +
                "JOIN xReferences ON BioModels.biomodelID = xReferences.biomodelID " +
                "WHERE xReferences.pathwayID = ? AND (hasApproval = ? OR hasMinPValue = ?)";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query, pathWayIdentifier, true, true);
        while (rowSet.next()) {
            bioModelsIdentifiers.add(rowSet.getString(1));
        }
        closeConnection();
        return bioModelsIdentifiers;
    }

    public Set<String> getAllPathwayIdentifier(String bioModelIdentifier) {
        Set<String> pathwayIdentifiers = new HashSet<>();
        String query = "SELECT Pathways.pathwayID FROM Pathways " +
                "JOIN xReferences ON Pathways.pathwayID = xReferences.pathwayID " +
                "WHERE xReferences.biomodelID = ? AND (hasApproval = ? OR hasMinPValue = ?)";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query, bioModelIdentifier, true, true);
        while (rowSet.next()) {
            pathwayIdentifiers.add(rowSet.getString(1));
        }
        closeConnection();
        return pathwayIdentifiers;
    }

    public void getAmount() {
        Set<String> pathwayIdentifiers = new HashSet<>();
        String query = "SELECT COUNT(*) FROM xReferences ";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query);
        while (rowSet.next()) {
            System.out.println(rowSet.getString(1));
        }
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
