package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.uniprot.impl.OrganismImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrganismFactory {
    public final static Pattern ORGANISM_PATTERN=Pattern.compile("([a-zA-Z 0-9]+)((( \\()([a-zA-Z 0-9]+)(\\)))"
            + "(( \\()([a-zA-Z 0-9,]+)(\\)))?)?");
    public static Organism createOrganism(long taxId, String scientificName){
        return createOrganism(taxId, scientificName, "");
    }
    public static Organism createOrganism(long taxId, String scientificName, String commonName){
        return createOrganism(taxId, scientificName, commonName, Collections.emptyList());
    }
    public static Organism createOrganism(long taxId, String scientificName, String commonName, List<String> synonyms){
        return new OrganismImpl(taxId, scientificName, commonName, synonyms);
    }
    
    public static Organism parse(long taxId, String organismStr){
        Matcher matcher = OrganismFactory.ORGANISM_PATTERN.matcher(organismStr);
        if(!matcher.matches()){
            throw new IllegalArgumentException("Organism String cannot be parsed: " + organismStr);
        }
        String scientificName = matcher.group(1);
        String commonName ="";
        if(matcher.group(5) !=null){
            commonName = matcher.group(5);
        }
        String synonymStr = matcher.group(9);
        List<String> synonyms =null;
        if(synonymStr !=null){
            synonyms= Arrays.asList(synonymStr.split(", "));
        }else{
            synonyms =Collections.emptyList();
        }
        return createOrganism(taxId, scientificName,  commonName, synonyms);
    }
}
