package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.EvidenceBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.EvidencedValueBuilder;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public class BuilderTestHelper {
    static List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(new EvidenceBuilder()
                              .databaseName("PROSITE-ProRule")
                              .databaseName("PRU10028")
                              .evidenceCode(EvidenceCode.ECO_0000255)
                              .build());
        evidences.add(new EvidenceBuilder()
                              .databaseName("PIRNR")
                              .databaseName("PIRNR001361")
                              .evidenceCode(EvidenceCode.ECO_0000256)
                              .build());
        return evidences;
    }

    static List<EvidencedValue> createEvidenceValues() {
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(new EvidencedValueBuilder().value("value1").evidences(emptyList()).build());
        evidencedValues.add(new EvidencedValueBuilder().value("value2").evidences(emptyList()).build());
        return evidencedValues;
    }
}
