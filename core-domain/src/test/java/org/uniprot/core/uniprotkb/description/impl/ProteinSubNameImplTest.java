package org.uniprot.core.uniprotkb.description.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.description.ProteinSubName;

import java.util.Collections;

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
        ProteinSubName obj = ProteinSubNameBuilder.from(impl).build();

        assertTrue(impl.isValid());
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
