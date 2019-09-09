package org.uniprot.core.uniprot.evidence.impl;

import org.junit.jupiter.api.Test;

import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EvidencedValueImplTest {

    @Test
    void testEmptyEvidence() {
        String value = "some value";
        List<Evidence> evidences = Collections.emptyList();
        EvidencedValueImpl evidencedValue = new EvidencedValueImpl(value, evidences);
        assertEquals(value, evidencedValue.getValue());
        assertEquals(evidences, evidencedValue.getEvidences());
    }

    @Test
    void testTwoEvidences() {
        String value = "some values";
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(new EvidenceImpl(
                EvidenceCode.ECO_0000313, "Ensembl", "ENSP0001324"
        ));
        evidences.add(new EvidenceImpl(
                EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"
        ));
        EvidencedValueImpl evidencedValue = new EvidencedValueImpl(value, evidences);
        assertEquals(value, evidencedValue.getValue());
        assertEquals(evidences, evidencedValue.getEvidences());
    }
}
