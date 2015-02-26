package org.reactome.server.core.utils;


import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import org.reactome.server.core.entrypoint.Models2Pathways;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class JSAPHandler {
    public static JSAPResult ArgumentHandler(String[] args) {
        JSAP jsap = new JSAP();
        FlaggedOption opt1 = new FlaggedOption("database")
                .setStringParser(JSAP.STRING_PARSER)
                .setRequired(true)
                .setShortFlag('d')
                .setLongFlag("database");
        opt1.setHelp("Path to database");

        FlaggedOption opt2 = new FlaggedOption("username")
                .setStringParser(JSAP.STRING_PARSER)
                .setRequired(true)
                .setShortFlag('u')
                .setLongFlag("username");
        opt2.setHelp("The database user");

        FlaggedOption opt3 = new FlaggedOption("password")
                .setStringParser(JSAP.STRING_PARSER)
                .setRequired(true)
                .setShortFlag('p')
                .setLongFlag("password");
        opt3.setHelp("The password to connect to the database");

        FlaggedOption opt4 = new FlaggedOption("significantPValue")
                .setStringParser(JSAP.STRING_PARSER)
                .setRequired(false)
                .setDefault(String.valueOf(0.05))
                .setLongFlag("significantPValue");
        opt4.setHelp("Value of the pValue for significant results");

        FlaggedOption opt5 = new FlaggedOption("extendedPValue")
                .setStringParser(JSAP.STRING_PARSER)
                .setRequired(false)
                .setDefault(String.valueOf(0.1))
                .setLongFlag("extendedPValue");
        opt5.setHelp("Value of the pValue for possible results");

        FlaggedOption opt6 = new FlaggedOption("output")
                .setStringParser(JSAP.STRING_PARSER)
                .setRequired(false)
                .setShortFlag('o')
                .setLongFlag("output");
        opt1.setHelp("Path to output tsv");

        try {
            jsap.registerParameter(opt1);
            jsap.registerParameter(opt2);
            jsap.registerParameter(opt3);
            jsap.registerParameter(opt4);
            jsap.registerParameter(opt5);
            jsap.registerParameter(opt6);
        } catch (JSAPException e) {
            e.printStackTrace();
        }

        JSAPResult jsapResult = jsap.parse(args);

        if (!jsapResult.success()) {
            System.err.println();
            System.err.println("Usage: java "
                    + Models2Pathways.class.getName());
            System.err.println("                "
                    + jsap.getUsage());
            System.err.println();
            System.err.println(jsap.getHelp());
            System.exit(1);
        }
        return jsapResult;
    }
}
