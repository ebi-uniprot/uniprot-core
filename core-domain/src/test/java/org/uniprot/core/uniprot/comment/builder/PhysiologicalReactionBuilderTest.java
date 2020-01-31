package org.uniprot.core.uniprot.comment.builder;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidence;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.PhysiologicalReaction;

class PhysiologicalReactionBuilderTest {
    @Test
    void canCreateBuilderFromInstance() {
        PhysiologicalReaction obj = new PhysiologicalReactionBuilder().build();
        PhysiologicalReactionBuilder builder = PhysiologicalReactionBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        PhysiologicalReaction obj = new PhysiologicalReactionBuilder().build();
        PhysiologicalReaction obj2 = new PhysiologicalReactionBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }

    @Test
    void canAddSingleEvidence() {
        PhysiologicalReaction obj =
                new PhysiologicalReactionBuilder().addEvidence(createEvidence()).build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void nullEvidence_willBeIgnore() {
        PhysiologicalReaction obj = new PhysiologicalReactionBuilder().addEvidence(null).build();
        assertNotNull(obj.getEvidences());
        assertTrue(obj.getEvidences().isEmpty());
        assertFalse(obj.hasEvidences());
    }

    @Test
    void evidences_willConvertUnModifiable_toModifiable() {
        PhysiologicalReaction obj =
                new PhysiologicalReactionBuilder()
                        .evidences(Collections.emptyList())
                        .addEvidence(createEvidence())
                        .build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void canAddListEvidences() {
        PhysiologicalReaction obj =
                new PhysiologicalReactionBuilder().evidences(createEvidences()).build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }
}
