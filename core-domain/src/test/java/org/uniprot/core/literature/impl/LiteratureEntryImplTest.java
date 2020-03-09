package org.uniprot.core.literature.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.ObjectsForTests;
import org.uniprot.core.literature.LiteratureEntry;

class LiteratureEntryImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        LiteratureEntry obj = new LiteratureEntryImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        LiteratureEntry impl = ObjectsForTests.createCompleteLiteratureEntry();
        LiteratureEntry obj = LiteratureEntryBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void toStringMethod() {
        assertNotNull(ObjectsForTests.createCompleteLiteratureEntry().toString());
    }
}
