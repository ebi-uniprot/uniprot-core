package org.uniprot.core.parser.tsv.uniprot;

import java.util.List;

import org.uniprot.core.uniprot.taxonomy.OrganismName;

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
