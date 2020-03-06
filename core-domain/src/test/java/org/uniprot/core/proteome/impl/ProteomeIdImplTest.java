package org.uniprot.core.proteome.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.ProteomeId;

class ProteomeIdImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        ProteomeId obj = new ProteomeIdImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        ProteomeId impl = new ProteomeIdImpl("value");
        ProteomeId obj = ProteomeIdBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
