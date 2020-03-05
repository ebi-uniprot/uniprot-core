package org.uniprot.core.uniprot.evidence;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EvidenceDatabaseTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        EvidenceDatabase obj = new EvidenceDatabase();
        assertNotNull(obj);
    }

    @Test
    void twoObjsWithNullNamesAreEqual() {
        assertEquals(new EvidenceDatabase(null), new EvidenceDatabase(null));
    }
}
