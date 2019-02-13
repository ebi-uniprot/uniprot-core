package uk.ac.ebi.uniprot.parser.tsv.uniprot;

import uk.ac.ebi.uniprot.domain.taxonomy.OrganismName;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;

import java.util.List;
import java.util.stream.Collectors;

public class EntryMapUtil {
    public static String evidencesToString(List<Evidence> evidences) {
        if ((evidences == null) || evidences.isEmpty())
            return "";
        return evidences.stream().map(Evidence::toString).collect(Collectors.joining(", ", "{", "}"));
    }

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

    public static String getNoteString(Note note) {
        return " Note=" + note.getTexts().stream().map(EntryMapUtil::evidencedValueToString)
                .collect(Collectors.joining("; "));
    }

    public static String evidencedValueToString(EvidencedValue evidencedValue) {
        String result = evidencedValue.getValue();
        if(evidencedValue.getEvidences() != null && !evidencedValue.getEvidences().isEmpty()){
            result += " "+evidencesToString(evidencedValue.getEvidences());
        }
        return result;
    }

}
