package org.reactome.server.core.helper;

import org.reactome.server.core.utils.Annotation;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */
public class TrivialChemicals {
    private Set<Annotation> trivialChemicals = new HashSet<>();

    public TrivialChemicals() {
        this.trivialChemicals.add(new Annotation("chebi", "15377"));//H2O
        this.trivialChemicals.add(new Annotation("chebi", "16761"));//ADP
        this.trivialChemicals.add(new Annotation("chebi", "15422"));//ATP
        this.trivialChemicals.add(new Annotation("chebi", "16027"));//AMP
        this.trivialChemicals.add(new Annotation("chebi", "15996"));//GTP
        this.trivialChemicals.add(new Annotation("chebi", "17552"));//GDP
        this.trivialChemicals.add(new Annotation("chebi", "17659"));//UDP
        this.trivialChemicals.add(new Annotation("chebi", "15713"));//UTP
        this.trivialChemicals.add(new Annotation("chebi", "15378"));//H+
        this.trivialChemicals.add(new Annotation("chebi", "16234"));//OH-
        this.trivialChemicals.add(new Annotation("chebi", "18367"));//Pi
        this.trivialChemicals.add(new Annotation("chebi", "29888"));//PPi
        this.trivialChemicals.add(new Annotation("chebi", "13392"));//NADPH
        this.trivialChemicals.add(new Annotation("chebi", "18009"));//NADP+
        this.trivialChemicals.add(new Annotation("chebi", "16908"));//NADH
        this.trivialChemicals.add(new Annotation("chebi", "16238"));//FAD
        this.trivialChemicals.add(new Annotation("chebi", "17877"));//FADH2
        this.trivialChemicals.add(new Annotation("chebi", "15346"));//CoA
        this.trivialChemicals.add(new Annotation("chebi", "15379"));//O2
        this.trivialChemicals.add(new Annotation("chebi", "16526"));//CO2
        this.trivialChemicals.add(new Annotation("chebi", "15846"));//NAD+
        this.trivialChemicals.add(new Annotation("chebi", "17345"));//GMP
        this.trivialChemicals.add(new Annotation("chebi", "16174"));//dADP
        this.trivialChemicals.add(new Annotation("chebi", "17713"));//dAMP
        this.trivialChemicals.add(new Annotation("chebi", "16284"));//dATP
        this.trivialChemicals.add(new Annotation("chebi", "28862"));//dGDP
        this.trivialChemicals.add(new Annotation("chebi", "16192"));//dGMP
        this.trivialChemicals.add(new Annotation("chebi", "16497"));//dGTP
        this.trivialChemicals.add(new Annotation("chebi", "17625"));//dUTP
        this.trivialChemicals.add(new Annotation("chebi", "15713"));//UTP
        this.trivialChemicals.add(new Annotation("chebi", "16695"));//UMP
        this.trivialChemicals.add(new Annotation("chebi", "18009"));//NADP+

        this.trivialChemicals.add(new Annotation("obo.chebi", "15377"));//H2O
        this.trivialChemicals.add(new Annotation("obo.chebi", "16761"));//ADP
        this.trivialChemicals.add(new Annotation("obo.chebi", "15422"));//ATP
        this.trivialChemicals.add(new Annotation("obo.chebi", "16027"));//AMP
        this.trivialChemicals.add(new Annotation("obo.chebi", "15996"));//GTP
        this.trivialChemicals.add(new Annotation("obo.chebi", "17552"));//GDP
        this.trivialChemicals.add(new Annotation("obo.chebi", "17659"));//UDP
        this.trivialChemicals.add(new Annotation("obo.chebi", "15713"));//UTP
        this.trivialChemicals.add(new Annotation("obo.chebi", "15378"));//H+
        this.trivialChemicals.add(new Annotation("obo.chebi", "16234"));//OH-
        this.trivialChemicals.add(new Annotation("obo.chebi", "18367"));//Pi
        this.trivialChemicals.add(new Annotation("obo.chebi", "29888"));//PPi
        this.trivialChemicals.add(new Annotation("obo.chebi", "13392"));//NADPH
        this.trivialChemicals.add(new Annotation("obo.chebi", "18009"));//NADP+
        this.trivialChemicals.add(new Annotation("obo.chebi", "16908"));//NADH
        this.trivialChemicals.add(new Annotation("obo.chebi", "16238"));//FAD
        this.trivialChemicals.add(new Annotation("obo.chebi", "17877"));//FADH2
        this.trivialChemicals.add(new Annotation("obo.chebi", "15346"));//CoA
        this.trivialChemicals.add(new Annotation("obo.chebi", "15379"));//O2
        this.trivialChemicals.add(new Annotation("obo.chebi", "16526"));//CO2
        this.trivialChemicals.add(new Annotation("obo.chebi", "15846"));//NAD+
        this.trivialChemicals.add(new Annotation("obo.chebi", "17345"));//GMP
        this.trivialChemicals.add(new Annotation("obo.chebi", "16174"));//dADP
        this.trivialChemicals.add(new Annotation("obo.chebi", "17713"));//dAMP
        this.trivialChemicals.add(new Annotation("obo.chebi", "16284"));//dATP
        this.trivialChemicals.add(new Annotation("obo.chebi", "28862"));//dGDP
        this.trivialChemicals.add(new Annotation("obo.chebi", "16192"));//dGMP
        this.trivialChemicals.add(new Annotation("obo.chebi", "16497"));//dGTP
        this.trivialChemicals.add(new Annotation("obo.chebi", "17625"));//dUTP
        this.trivialChemicals.add(new Annotation("obo.chebi", "15713"));//UTP
        this.trivialChemicals.add(new Annotation("obo.chebi", "16695"));//UMP
        this.trivialChemicals.add(new Annotation("obo.chebi", "18009"));//NADP+
    }

    public Set<Annotation> getTrivialChemicals() {
        return trivialChemicals;
    }
}


































