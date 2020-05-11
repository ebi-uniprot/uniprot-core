package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.Reaction;
import org.uniprot.core.uniprotkb.comment.ReactionDatabase;

import java.util.Collections;

class ReactionBuilderTest {
    @Test
    void canCreateBuilderFromInstance() {
        Reaction obj = new ReactionBuilder().build();
        ReactionBuilder builder = ReactionBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        Reaction obj = new ReactionBuilder().build();
        Reaction obj2 = new ReactionBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }

    @Test
    void canAddSingleEvidence() {
        Reaction obj = new ReactionBuilder().evidencesAdd(createEvidence()).build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void nullEvidence_willBeIgnore() {
        Reaction obj = new ReactionBuilder().evidencesAdd(null).build();
        assertNotNull(obj.getEvidences());
        assertTrue(obj.getEvidences().isEmpty());
        assertFalse(obj.hasEvidences());
    }

    @Test
    void evidences_willConvertUnModifiable_toModifiable() {
        Reaction obj =
                new ReactionBuilder()
                        .evidencesSet(Collections.emptyList())
                        .evidencesAdd(createEvidence())
                        .build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void canAddListEvidences() {
        Reaction obj = new ReactionBuilder().evidencesSet(createEvidences()).build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void canCreateEcFromString() {
        Reaction obj = new ReactionBuilder().ecNumber("123").build();
        assertTrue(obj.hasEcNumber());
    }

    @Test
    void canAddReactionReference() {
        Reaction obj =
                new ReactionBuilder()
                        .reactionCrossReferencesAdd(crossReference(ReactionDatabase.RHEA, "123"))
                        .build();
        assertTrue(obj.hasReactionCrossReferences());
    }
}
