package org.uniprot.core.uniprotkb.evidence;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EvidenceDatabaseCategoryTest {
    @Test
    void I() {
        assertEquals("I", EvidenceDatabaseCategory.I.toDisplayName());
    }

    @Test
    void C() {
        assertEquals("C", EvidenceDatabaseCategory.C.toDisplayName());
    }

    @Test
    void A() {
        assertEquals("A", EvidenceDatabaseCategory.A.toDisplayName());
    }
}
