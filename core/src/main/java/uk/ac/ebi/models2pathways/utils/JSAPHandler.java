package uk.ac.ebi.models2pathways.utils;


import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class JSAPHandler {
    public static JSAPResult ArgumentHandler(String[] args) {
        JSAP jsap = new JSAP();
        FlaggedOption opt1 = new FlaggedOption("database")
                .setStringParser(JSAP.STRING_PARSER)
                .setRequired(true)
                .setShortFlag('d');

        FlaggedOption opt2 = new FlaggedOption("username")
                .setStringParser(JSAP.STRING_PARSER)
                .setRequired(true)
                .setShortFlag('u');

        FlaggedOption opt3 = new FlaggedOption("password")
                .setStringParser(JSAP.STRING_PARSER)
                .setRequired(true)
                .setShortFlag('p');

        FlaggedOption opt4 = new FlaggedOption("significantPValue")
                .setStringParser(JSAP.STRING_PARSER)
                .setRequired(false)
                .setDefault(String.valueOf(0.05))
                .setLongFlag("pValue");

        FlaggedOption opt5 = new FlaggedOption("extendedPValue")
                .setStringParser(JSAP.STRING_PARSER)
                .setRequired(false)
                .setDefault(String.valueOf(0.1))
                .setLongFlag("extendedPValue");
        try {
            jsap.registerParameter(opt1);
            jsap.registerParameter(opt2);
            jsap.registerParameter(opt3);
            jsap.registerParameter(opt4);
            jsap.registerParameter(opt5);
        } catch (JSAPException e) {
            e.printStackTrace();
        }
        return jsap.parse(args);
    }

}
