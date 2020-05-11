package org.uniprot.core.uniprotkb.evidence.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.evidence.EvidenceLine;

import java.time.LocalDate;
import java.time.Month;

class EvidenceLineImplTest {
    private EvidenceLine impl =
            new EvidenceLineImpl(
                    "ECO:0000269|PubMed:22481068",
                    LocalDate.of(2015, Month.AUGUST, 2),
                    "som curator");

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        EvidenceLine obj = new EvidenceLineImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        EvidenceLine obj = EvidenceLineBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
