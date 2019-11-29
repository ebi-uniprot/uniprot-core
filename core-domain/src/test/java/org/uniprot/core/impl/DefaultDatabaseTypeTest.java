package org.uniprot.core.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DatabaseType;

class DefaultDatabaseTypeTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        DatabaseType obj = new DefaultDatabaseType();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        DatabaseType impl = new DefaultDatabaseType("abc");
        DatabaseType obj = new DefaultDatabaseType("abc");
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalNullObject() {
        DatabaseType impl = new DefaultDatabaseType(null);
        DatabaseType obj = new DefaultDatabaseType(null);
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
