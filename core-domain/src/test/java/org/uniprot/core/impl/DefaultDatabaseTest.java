package org.uniprot.core.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Database;

class DefaultDatabaseTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Database obj = new DefaultDatabase();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        Database impl = new DefaultDatabase("abc");
        Database obj = new DefaultDatabase("abc");
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalNullObject() {
        Database impl = new DefaultDatabase(null);
        Database obj = new DefaultDatabase(null);
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
