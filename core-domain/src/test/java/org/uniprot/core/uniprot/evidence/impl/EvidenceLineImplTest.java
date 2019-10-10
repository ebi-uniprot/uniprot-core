package org.uniprot.core.uniprot.evidence.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.evidence.EvidenceLine;
import org.uniprot.core.uniprot.evidence.builder.EvidenceLineBuilder;

class EvidenceLineImplTest {
    EvidenceLine impl =
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
        EvidenceLine obj = new EvidenceLineBuilder().from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
