package org.uniprot.core.uniprot.comment.builder;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidence;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.RnaEdPosition;

class RnaEditingPositionBuilderTest {
    @Test
    void canAddSingleEvidence() {
        RnaEdPosition obj = new RnaEditingPositionBuilder().addEvidence(createEvidence()).build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void nullEvidence_willBeIgnore() {
        RnaEdPosition obj = new RnaEditingPositionBuilder().addEvidence(null).build();
        assertNotNull(obj.getEvidences());
        assertTrue(obj.getEvidences().isEmpty());
        assertFalse(obj.hasEvidences());
    }

    @Test
    void evidences_willConvertUnModifiable_toModifiable() {
        RnaEdPosition obj =
                new RnaEditingPositionBuilder()
                        .evidences(Collections.emptyList())
                        .addEvidence(createEvidence())
                        .build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void canAddListEvidences() {
        RnaEdPosition obj = new RnaEditingPositionBuilder().evidences(createEvidences()).build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void canCreateBuilderFromInstance() {
        RnaEdPosition obj = new RnaEditingPositionBuilder().build();
        RnaEditingPositionBuilder builder = new RnaEditingPositionBuilder().from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        RnaEdPosition obj = new RnaEditingPositionBuilder().build();
        RnaEdPosition obj2 = new RnaEditingPositionBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
