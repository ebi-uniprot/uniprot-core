package org.uniprot.core.proteome.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.proteomeXReferenceTypes;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.Component;
import org.uniprot.core.proteome.ComponentType;
import org.uniprot.core.proteome.builder.ComponentBuilder;

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
                        "name", "des", 20, ComponentType.PRIMARY, proteomeXReferenceTypes());
        Component obj = new ComponentBuilder().from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
