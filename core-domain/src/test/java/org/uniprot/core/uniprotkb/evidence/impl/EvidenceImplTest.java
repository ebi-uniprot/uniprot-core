package org.uniprot.core.uniprotkb.evidence.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidenceCode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class EvidenceImplTest {
    Evidence impl = new EvidenceImpl(EvidenceCode.ECO_0000256, "EnsemblPlants", "id");

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Evidence obj = new EvidenceImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        Evidence obj = EvidenceBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void toStringAndGetValueAreSame() {
        assertEquals(impl.getValue(), impl.toString());
    }

    @Test
    void toStringAndGetValueAreSameWhenSourceIsNull() {
        Evidence impl = new EvidenceImpl(EvidenceCode.ECO_0000256, null);
        assertEquals(impl.getValue(), impl.toString());
    }

    @Test
    void whenReferenceItWillContainId() {
        Evidence impl = new EvidenceImpl(EvidenceCode.ECO_0000256, "Reference", "id");
        assertEquals("ECO:0000256|id", impl.getValue());
    }

    @Test
    void canGetEvdCode() {
        assertEquals(EvidenceCode.ECO_0000256, impl.getEvidenceCode());
    }

    @Test
    void canGetSource() {
        assertEquals("id", impl.getEvidenceCrossReference().getId());
    }

    @Test
    void compareTo_caseInsensitive_onValue() {
        Evidence impl = new EvidenceImpl(EvidenceCode.ECO_0000256, null);
        Evidence impl2 = new EvidenceImpl(EvidenceCode.ECO_0000255, null);
        List<Evidence> list = Arrays.asList(impl, impl2);
        Collections.sort(list);

        assertEquals(EvidenceCode.ECO_0000255, list.get(0).getEvidenceCode());
    }
}
