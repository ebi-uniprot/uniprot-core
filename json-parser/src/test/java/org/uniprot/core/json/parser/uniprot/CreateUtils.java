package org.uniprot.core.json.parser.uniprot;

import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidenceCode;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilder;
import org.uniprot.core.uniprotkb.evidence.impl.EvidencedValueBuilder;

import java.util.Collections;
import java.util.List;

/** @author lgonzales */
public class CreateUtils {

    public static List<Evidence> createEvidenceList(String evidenceStr) {
        return Collections.singletonList(createEvidence(evidenceStr));
    }

    public static Evidence createEvidence(String evidenceStr) {
        String[] splittedEvidence = evidenceStr.split("\\|");
        EvidenceCode code = EvidenceCode.typeOf(splittedEvidence[0]);
        String[] splittedDatabase = splittedEvidence[1].split(":");
        return new EvidenceBuilder()
                .evidenceCode(code)
                .databaseName(splittedDatabase[0])
                .databaseId(splittedDatabase[1])
                .build();
    }

    public static List<EvidencedValue> createEvidencedValueList(String value, String evidenceStr) {
        return Collections.singletonList(createEvidencedValue(value, evidenceStr));
    }

    public static EvidencedValue createEvidencedValue(String value, String evidenceStr) {
        List<Evidence> evidences = Collections.singletonList(createEvidence(evidenceStr));
        return new EvidencedValueBuilder(value, evidences).build();
    }
}
