package uk.ac.ebi.uniprot.json.parser.uniprot;

import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.EvidenceBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.EvidencedValueBuilder;

import java.util.Collections;
import java.util.List;
/**
 *
 * @author lgonzales
 */
public class CreateUtils {

    public static List<Evidence> createEvidenceList(String evidenceStr) {
        return Collections.singletonList(createEvidence(evidenceStr));
    }

    public static Evidence createEvidence(String evidenceStr) {
        String[] splittedEvidence = evidenceStr.split("\\|");
        EvidenceCode code = EvidenceCode.codeOf(splittedEvidence[0]);
        String[] splittedDatabase = splittedEvidence[1].split(":");
        return new EvidenceBuilder()
                .evidenceCode(code)
                .databaseName(splittedDatabase[0])
                .databaseId(splittedDatabase[1])
                .build();
    }

    public static List<EvidencedValue> createEvidencedValueList(String value, String evidenceStr) {
        return Collections.singletonList(createEvidencedValue(value,evidenceStr));
    }

    public static EvidencedValue createEvidencedValue(String value, String evidenceStr) {
        List<Evidence> evidences = Collections.singletonList(createEvidence(evidenceStr));
        return new EvidencedValueBuilder(value,evidences).build();
    }
}
