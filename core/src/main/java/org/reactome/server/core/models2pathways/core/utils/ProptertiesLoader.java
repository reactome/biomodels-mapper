package org.reactome.server.core.models2pathways.core.utils;

import org.reactome.server.core.models2pathways.core.model.Namespace;
import org.reactome.server.core.models2pathways.core.model.Specie;
import org.reactome.server.core.models2pathways.core.model.TrivialChemical;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class ProptertiesLoader {
    private static final String SEPARATOR = "\t";

    public static Set<Specie> readSpecies(String inputPath) throws IOException {
        Set<Specie> species = new HashSet<>();
        String line;
        while ((line = readFile(inputPath).readLine()) != null) {
            String[] content = line.split(SEPARATOR);
            species.add(new Specie(Long.valueOf(content[0]), Long.valueOf(content[1]), content[2]));
        }
        return species;
    }

    public static Set<Namespace> readNamespaces(String inputPath) throws IOException {
        Set<Namespace> namespaces = new HashSet<>();
        String line;
        while ((line = readFile(inputPath).readLine()) != null) {
            String[] content = line.split(SEPARATOR);
            namespaces.add(new Namespace(content[0], Boolean.valueOf(content[1])));
        }
        return namespaces;
    }

    public static Set<TrivialChemical> readTrivialChemicals(String inputPath) throws IOException {
        Set<TrivialChemical> trivialChemicals = new HashSet<>();
        String line;
        while ((line = readFile(inputPath).readLine()) != null) {
            String[] content = line.split(SEPARATOR);
            trivialChemicals.add(new TrivialChemical(Long.valueOf(content[0]), content[1]));
        }
        return trivialChemicals;
    }
    
    private static BufferedReader readFile(String inputPath){
        BufferedReader bufferedReader = null;
        try {
            bufferedReader =  new BufferedReader(new FileReader(inputPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return bufferedReader;
    }
}
