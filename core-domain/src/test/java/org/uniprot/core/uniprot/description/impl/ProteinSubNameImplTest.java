package org.uniprot.core.uniprot.description.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.description.ProteinSubName;
import org.uniprot.core.uniprot.description.builder.ProteinSubNameBuilder;

class ProteinSubNameImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        ProteinSubName obj = new ProteinSubNameImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        ProteinSubName impl =
                new ProteinSubNameImpl(new NameImpl("abc", null), Collections.emptyList());
        ProteinSubName obj = new ProteinSubNameBuilder().from(impl).build();

        assertTrue(impl.isValid());
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
