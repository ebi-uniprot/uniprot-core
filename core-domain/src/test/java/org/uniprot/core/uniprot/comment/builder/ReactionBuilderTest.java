package org.uniprot.core.uniprot.comment.builder;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidence;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.impl.DBCrossReferenceImpl;
import org.uniprot.core.uniprot.comment.Reaction;
import org.uniprot.core.uniprot.comment.ReactionReferenceType;

class ReactionBuilderTest {
    @Test
    void canCreateBuilderFromInstance() {
        Reaction obj = new ReactionBuilder().build();
        ReactionBuilder builder = new ReactionBuilder().from(obj);
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
        Reaction obj = new ReactionBuilder().addEvidence(createEvidence()).build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void nullEvidence_willBeIgnore() {
        Reaction obj = new ReactionBuilder().addEvidence(null).build();
        assertNotNull(obj.getEvidences());
        assertTrue(obj.getEvidences().isEmpty());
        assertFalse(obj.hasEvidences());
    }

    @Test
    void evidences_willConvertUnModifiable_toModifiable() {
        Reaction obj =
                new ReactionBuilder()
                        .evidences(Collections.emptyList())
                        .addEvidence(createEvidence())
                        .build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void canAddListEvidences() {
        Reaction obj = new ReactionBuilder().evidences(createEvidences()).build();
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
                        .addReactionReference(
                                new DBCrossReferenceImpl<>(ReactionReferenceType.RHEA, "123"))
                        .build();
        assertTrue(obj.hasReactionReferences());
    }
}
