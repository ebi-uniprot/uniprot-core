package org.uniprot.core.uniprot.description.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.description.builder.NameBuilder;

class NameImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Name obj = new NameImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        Name impl = new NameImpl("abc", createEvidences());
        Name obj = NameBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void nonNullValidName() {
        Name impl = new NameImpl("abc", null);
        assertTrue(impl.isValid());
    }
}
