package org.uniprot.core.uniprotkb.evidence;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EvidenceDatabaseCategoryTest {
    @Test
    void I() {
        assertEquals("I", EvidenceDatabaseCategory.I.getDisplayName());
    }

    @Test
    void C() {
        assertEquals("C", EvidenceDatabaseCategory.C.getDisplayName());
    }

    @Test
    void A() {
        assertEquals("A", EvidenceDatabaseCategory.A.getDisplayName());
    }
}
