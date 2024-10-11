package org.uniprot.core.uniparc.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.Proteome;

import static org.junit.jupiter.api.Assertions.*;

class ProteomeImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Proteome obj = new ProteomeImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        Proteome impl = new ProteomeImpl("id", "component");
        Proteome obj = ProteomeBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}