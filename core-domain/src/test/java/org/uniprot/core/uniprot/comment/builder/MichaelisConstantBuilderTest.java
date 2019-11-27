package org.uniprot.core.uniprot.comment.builder;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidence;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.MichaelisConstant;

class MichaelisConstantBuilderTest {
    @Test
    void canCreateBuilderFromInstance() {
        MichaelisConstant obj = new MichaelisConstantBuilder().build();
        MichaelisConstantBuilder builder = new MichaelisConstantBuilder().from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        MichaelisConstant obj = new MichaelisConstantBuilder().build();
        MichaelisConstant obj2 = new MichaelisConstantBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }

    @Test
    void canAddSingleEvidence() {
        MichaelisConstant obj =
                new MichaelisConstantBuilder().addEvidence(createEvidence()).build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void nullEvidence_willBeIgnore() {
        MichaelisConstant obj = new MichaelisConstantBuilder().addEvidence(null).build();
        assertNotNull(obj.getEvidences());
        assertTrue(obj.getEvidences().isEmpty());
        assertFalse(obj.hasEvidences());
    }

    @Test
    void evidences_willConvertUnModifiable_toModifiable() {
        MichaelisConstant obj =
                new MichaelisConstantBuilder()
                        .evidences(Collections.emptyList())
                        .addEvidence(createEvidence())
                        .build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void canAddListEvidences() {
        MichaelisConstant obj = new MichaelisConstantBuilder().evidences(createEvidences()).build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }
}
