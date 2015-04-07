package org.reactome.server.core.models2pathways.core.utils;

import org.reactome.server.core.models2pathways.core.model.Namespace;
import org.reactome.server.core.models2pathways.core.model.Specie;
import org.reactome.server.core.models2pathways.core.model.TrivialChemical;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class PropertiesLoader {
    private static final String SEPARATOR = "\t";

    private static BufferedReader readFile(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    public Set<Specie> getSpecies() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("species.properties");
        Set<Specie> species = new HashSet<>();
        String line;
        while ((line = readFile(inputStream).readLine()) != null) {
            String[] content = line.split(SEPARATOR);
            System.out.println(content.length);
            species.add(new Specie(Long.valueOf(content[0]), Long.valueOf(content[1]), content[2]));
        }
        return species;
    }

    public Set<Namespace> getNamespaces() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("namespaces.properties");
        Set<Namespace> namespaces = new HashSet<>();
        String line;
        while ((line = readFile(inputStream).readLine()) != null) {
            String[] content = line.split(SEPARATOR);
            namespaces.add(new Namespace(content[0], Boolean.valueOf(content[1])));
        }
        return namespaces;
    }

    public Set<TrivialChemical> getTrivialChemicals() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("trivialchemicals.properties");
        Set<TrivialChemical> trivialChemicals = new HashSet<>();
        String line;
        while ((line = readFile(inputStream).readLine()) != null) {
            String[] content = line.split(SEPARATOR);
            trivialChemicals.add(new TrivialChemical(Long.valueOf(content[0]), content[1]));
        }
        return trivialChemicals;
    }
}