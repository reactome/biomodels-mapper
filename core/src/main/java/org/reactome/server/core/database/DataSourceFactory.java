package org.reactome.server.core.database;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.reactome.server.core.utils.PropertiesHelper;

import java.util.Properties;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class DataSourceFactory {
    private static Logger logger = Logger.getLogger(DataSourceFactory.class.getName());

    private static String databaseLocation;
    private static String username;
    private static String password;

    private static final PropertiesHelper propertiesHelper = new PropertiesHelper();


    public static BasicDataSource getDatabaseConnection() {
        Properties properties = getProperties();
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(properties.getProperty("models2pathways.database.driver"));
        basicDataSource.setUrl(properties.getProperty("models2pathways.database.url") + databaseLocation + ";");
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
        basicDataSource.setMaxActive(Integer.parseInt(properties.getProperty("models2pathways.database.connections")));
        return basicDataSource;
    }

    public static String getDatabaseLocation() {
        return databaseLocation;
    }

    public static void setDatabaseLocation(String databaseLocation) {
        DataSourceFactory.databaseLocation = databaseLocation;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        DataSourceFactory.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        DataSourceFactory.password = password;
    }

    private static Properties getProperties() {
        return propertiesHelper.getDBProperties();
    }
}