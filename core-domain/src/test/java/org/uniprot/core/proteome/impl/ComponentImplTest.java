package org.uniprot.core.proteome.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.proteomeXReferenceTypes;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.Component;
import org.uniprot.core.proteome.GenomeAnnotation;

class ComponentImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Component obj = new ComponentImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        GenomeAnnotation genomeAnnotation = new GenomeAnnotationBuilder()
                .source("source value")
                .url("url value")
                .build();

        Component impl =
                new ComponentImpl(
                        "name", "des", 20, genomeAnnotation, proteomeXReferenceTypes());
        Component obj = ComponentBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
