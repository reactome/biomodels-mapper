package uk.ac.ebi.biomodels.tools;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Maximilian Koch <mkoch@ebi.ac.uk>
 */
public class PropertiesLoader {

    public Properties getAnalysisURL() {
        Properties properties = new Properties();
        try {
            properties.load(PropertiesLoader.class.getClassLoader().getResourceAsStream("analysisURL.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
