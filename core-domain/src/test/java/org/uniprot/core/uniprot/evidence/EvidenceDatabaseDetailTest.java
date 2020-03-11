package org.uniprot.core.uniprot.evidence;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EvidenceDatabaseDetailTest {
    @Test
    void uriLinkWillBeNeverNull() {
        EvidenceDatabaseDetail e = new EvidenceDatabaseDetail();
        assertNotNull(e.getUriLink());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        EvidenceDatabaseDetail e = new EvidenceDatabaseDetail();
        assertNotNull(e);
    }

    @Test
    void defaultObjsAreEqual() {
        EvidenceDatabaseDetail obj = new EvidenceDatabaseDetail();
        EvidenceDatabaseDetail obj2 = new EvidenceDatabaseDetail();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }

    @Test
    void constructor_objsAreEqual() {
        EvidenceDatabaseDetail obj =
                new EvidenceDatabaseDetail("a", "b", EvidenceDatabaseCategory.A, null);
        EvidenceDatabaseDetail obj2 =
                new EvidenceDatabaseDetail("a", "b", EvidenceDatabaseCategory.A, null);
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }

    @Test
    void canGetCategory() {
        EvidenceDatabaseDetail obj =
                new EvidenceDatabaseDetail("a", "b", EvidenceDatabaseCategory.A, null);
        assertEquals(EvidenceDatabaseCategory.A, obj.getCategory());
    }

    @Test
    void canGetName() {
        EvidenceDatabaseDetail obj =
          new EvidenceDatabaseDetail("a", "b", EvidenceDatabaseCategory.A, null);
        assertEquals("a", obj.getName());
    }
}
