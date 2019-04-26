package uk.ac.ebi.uniprot.parser.tsv.uniprot;

import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.OrganismName;

import java.util.List;

public class EntryMapUtil {
    public static String convertOrganism(OrganismName organism) {
        StringBuilder sb = new StringBuilder();
        if (organism.getScientificName() != null && !organism.getScientificName().isEmpty()) {
            sb.append(organism.getScientificName());
        }
        if (organism.getCommonName() != null && !organism.getCommonName().isEmpty()) {
            sb.append(" (");
            sb.append(organism.getCommonName());
            sb.append(")");
        }
        List<String> synonyms = organism.getSynonyms();
        if (synonyms != null && !synonyms.isEmpty()) {
            sb.append(" (").append(String.join(", ", synonyms)).append(")");
        }
        return sb.toString();
    }


}
