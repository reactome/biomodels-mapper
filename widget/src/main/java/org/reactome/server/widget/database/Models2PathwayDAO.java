package org.reactome.server.widget.database;

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

    public Set<String> getAllBioModelsIDs() {
        Set<String> bioModelsIdentifiers = new HashSet<>();
        String query = "SELECT DISTINCT xReferences.biomodelID FROM xReferences " +
                "WHERE xReferences.hasMinPValue = 1";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query);
        while (rowSet.next()) {
            bioModelsIdentifiers.add(rowSet.getString(1));
        }
        closeConnection();
        return bioModelsIdentifiers;
    }

    private void closeConnection() {
        try {
            jdbcTemplate.getDataSource().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
