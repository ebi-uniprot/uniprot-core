package org.uniprot.core.proteome.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.proteomeXReferenceTypes;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.Component;

class ComponentImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Component obj = new ComponentImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        Component impl =
                new ComponentImpl(
                        "name", "des", 20, proteomeXReferenceTypes());
        Component obj = ComponentBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
