package uk.ac.ebi.models2pathways.database;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class DataSourceFactory {
    private static Logger logger = Logger.getLogger(DataSourceFactory.class.getName());

    private static String databaseLocation;
    private static String username;
    private static String password;

    public static BasicDataSource getDatabaseConnection() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("org.h2.Driver");
        basicDataSource.setUrl("jdbc:h2:" + databaseLocation);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
        basicDataSource.setMaxActive(5);
        return basicDataSource;
//        BasicDataSource basicDataSource = new BasicDataSource();
//        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        basicDataSource.setUrl("jdbc:mysql://localhost:3306/models2pathways");
//        basicDataSource.setUsername("root");
//        basicDataSource.setPassword("");
//        basicDataSource.setMaxActive(5);
//        return basicDataSource;
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
}