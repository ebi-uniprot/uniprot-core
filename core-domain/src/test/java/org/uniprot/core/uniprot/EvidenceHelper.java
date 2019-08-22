package org.uniprot.core.uniprot;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceCode;
import org.uniprot.core.uniprot.evidence.EvidencedValue;
import org.uniprot.core.uniprot.evidence.builder.EvidenceBuilder;
import org.uniprot.core.uniprot.evidence.builder.EvidencedValueBuilder;

import static java.util.Collections.emptyList;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public class EvidenceHelper {
    public static List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(new EvidenceBuilder()
                              .databaseName("PROSITE-ProRule")
                              .databaseId("PRU10028")
                              .evidenceCode(EvidenceCode.ECO_0000255)
                              .build());
        evidences.add(new EvidenceBuilder()
                              .databaseName("PIRNR")
                              .databaseId("PIRNR001361")
                              .evidenceCode(EvidenceCode.ECO_0000256)
                              .build());
        return evidences;
    }

    public static List<EvidencedValue> createEvidenceValuesWithoutEvidences() {
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(new EvidencedValueBuilder("value1", emptyList()).build());
        evidencedValues.add(new EvidencedValueBuilder("value2", emptyList()).build());
        return evidencedValues;
    }

    public static List<EvidencedValue> createEvidenceValuesWithEvidences() {
        List<Evidence> evidences1 = new ArrayList<>();
        evidences1.add(new EvidenceBuilder()
                               .databaseName("Ensembl")
                               .databaseId("ENSP0001324")
                               .evidenceCode(EvidenceCode.ECO_0000313)
                               .build());
        evidences1.add(new EvidenceBuilder()
                               .databaseName("PIRNR")
                               .databaseName("PIRNR001361")
                               .evidenceCode(EvidenceCode.ECO_0000256)
                               .build());

        List<Evidence> evidences2 = new ArrayList<>();
        evidences1.add(new EvidenceBuilder()
                               .databaseName("Ensembl")
                               .databaseId("ENSP0001325")
                               .evidenceCode(EvidenceCode.ECO_0000313)
                               .build());

        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(new EvidencedValueBuilder("value1", evidences1).build());
        evidencedValues.add(new EvidencedValueBuilder("value2", evidences2).build());
        return evidencedValues;
    }
}