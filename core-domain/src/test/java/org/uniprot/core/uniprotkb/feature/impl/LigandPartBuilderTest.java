package org.uniprot.core.uniprotkb.feature.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.feature.LigandPart;

class LigandPartBuilderTest {

    @Test
    void canSetName() {
        LigandPart ligandPart = new LigandPartBuilder().name("Some name").build();
        assertEquals("Some name", ligandPart.getName());
    }

    @Test
    void canSetId() {
        LigandPart ligandPart = new LigandPartBuilder().id("ChEBI:CHEBI:3234").build();
        assertEquals("ChEBI:CHEBI:3234", ligandPart.getId());
    }

    @Test
    void canSetLabel() {
        LigandPart ligandPart = new LigandPartBuilder().label("Some label").build();
        assertEquals("Some label", ligandPart.getLabel());
    }

    @Test
    void canSetNote() {
        LigandPart ligandPart = new LigandPartBuilder().note("some note").build();
        assertEquals("some note", ligandPart.getNote());
    }

    @Test
    void canCreateBuilderFromInstance() {
        LigandPart obj = new LigandPartBuilder().build();
        LigandPartBuilder builder = LigandPartBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        LigandPart obj = new LigandPartBuilder().build();
        LigandPart obj2 = new LigandPartBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
