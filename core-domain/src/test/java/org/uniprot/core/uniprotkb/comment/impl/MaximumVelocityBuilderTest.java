package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidence;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.MaximumVelocity;

class MaximumVelocityBuilderTest {
    @Test
    void canCreateBuilderFromInstance() {
        MaximumVelocity obj = new MaximumVelocityBuilder().build();
        MaximumVelocityBuilder builder = MaximumVelocityBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        MaximumVelocity obj = new MaximumVelocityBuilder().build();
        MaximumVelocity obj2 = new MaximumVelocityBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }

    @Test
    void canAddSingleEvidence() {
        MaximumVelocity obj = new MaximumVelocityBuilder().evidencesAdd(createEvidence()).build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void nullEvidence_willBeIgnore() {
        MaximumVelocity obj = new MaximumVelocityBuilder().evidencesAdd(null).build();
        assertNotNull(obj.getEvidences());
        assertTrue(obj.getEvidences().isEmpty());
        assertFalse(obj.hasEvidences());
    }

    @Test
    void evidences_willConvertUnModifiable_toModifiable() {
        MaximumVelocity obj =
                new MaximumVelocityBuilder()
                        .evidencesSet(Collections.emptyList())
                        .evidencesAdd(createEvidence())
                        .build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void canAddListEvidences() {
        MaximumVelocity obj = new MaximumVelocityBuilder().evidencesSet(createEvidences()).build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }
}
