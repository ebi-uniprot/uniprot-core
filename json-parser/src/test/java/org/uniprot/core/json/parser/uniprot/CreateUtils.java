package org.uniprot.core.json.parser.uniprot;

import java.util.Collections;
import java.util.List;

import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceCode;
import org.uniprot.core.uniprot.evidence.EvidencedValue;
import org.uniprot.core.uniprot.evidence.builder.EvidenceBuilder;
import org.uniprot.core.uniprot.evidence.builder.EvidencedValueBuilder;
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
