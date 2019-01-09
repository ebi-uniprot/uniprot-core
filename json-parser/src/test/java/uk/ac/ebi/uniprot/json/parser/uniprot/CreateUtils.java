package uk.ac.ebi.uniprot.json.parser.uniprot;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;

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
        return UniProtFactory.INSTANCE.createEvidence(evidenceStr);
    }

    public static List<EvidencedValue> createEvidencedValueList(String value, String evidenceStr) {
        return Collections.singletonList(createEvidencedValue(value,evidenceStr));
    }

    public static EvidencedValue createEvidencedValue(String value, String evidenceStr) {
        List<Evidence> evidences = Collections.singletonList(createEvidence(evidenceStr));
        return UniProtFactory.INSTANCE.createEvidencedValue(value,evidences );
    }
}
