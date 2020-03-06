package org.uniprot.core.uniprot.evidence.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceCode;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

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
        evidences.add(new EvidenceImpl(EvidenceCode.ECO_0000313, "Ensembl", "ENSP0001324"));
        evidences.add(new EvidenceImpl(EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"));
        EvidencedValueImpl evidencedValue = new EvidencedValueImpl(value, evidences);
        assertEquals(value, evidencedValue.getValue());
        assertEquals(evidences, evidencedValue.getEvidences());
    }

    @Test
    void valueWillNeverBeNull() {
        EvidencedValueImpl evidencedValue = new EvidencedValueImpl(null, null);
        assertNotNull(evidencedValue.getValue());
        assertFalse(evidencedValue.hasValue());
    }

    @Test
    void evidencesWillNeverBeNull() {
        EvidencedValueImpl evidencedValue = new EvidencedValueImpl(null, null);
        assertNotNull(evidencedValue.getEvidences());
        assertFalse(evidencedValue.hasEvidences());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        EvidencedValueImpl obj = new EvidencedValueImpl();
        assertNotNull(obj);
    }

    @Test
    void toStringIsSeperatedBySpace() {
        EvidencedValueImpl evidencedValue = new EvidencedValueImpl("value", null);
        assertEquals(evidencedValue.toString(), evidencedValue.getDisplayed(" "));
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        EvidencedValueImpl impl = new EvidencedValueImpl("value", null);
        EvidencedValue obj = EvidencedValueBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
