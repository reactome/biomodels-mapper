package org.reactome.server.widget.database;

import org.apache.commons.dbcp.BasicDataSource;

import java.util.logging.Logger;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class DataSourceFactory {
    private static Logger logger = Logger.getLogger(DataSourceFactory.class.getName());

    private static String databaseDriverClass;
    private static String connectionURL;

    public static BasicDataSource getDatabaseConnection() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("org.h2.Driver");
        basicDataSource.setUrl("jdbc:h2:/Users/maximiliankoch/models2pathways/database/models2pathways;AUTO_SERVER=TRUE");
        basicDataSource.setUsername("Models2Pathways");
        basicDataSource.setPassword("Models2Pathways");
        basicDataSource.setMaxActive(5);
        return basicDataSource;
    }

    public void setDatabaseDriverClass(String databaseDriverClass) {
        DataSourceFactory.databaseDriverClass = databaseDriverClass;
    }

    public void setConnectionURL(String connectionURL) {
        DataSourceFactory.connectionURL = connectionURL;
    }
}
