package org.uniprot.core.proteome.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.RedundantProteome;
import org.uniprot.core.proteome.builder.RedundantProteomeBuilder;

class RedundantProteomeImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        RedundantProteome obj = new RedundantProteomeImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        RedundantProteome impl = new RedundantProteomeImpl(new ProteomeIdImpl("id"), 5.8F);
        RedundantProteome obj = new RedundantProteomeBuilder().from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
