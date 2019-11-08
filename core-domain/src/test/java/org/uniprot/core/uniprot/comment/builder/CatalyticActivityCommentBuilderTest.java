package org.uniprot.core.uniprot.comment.builder;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createPhyReaction;
import static org.uniprot.core.ObjectsForTests.createPhyReactions;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.CatalyticActivityComment;
import org.uniprot.core.uniprot.comment.Reaction;

class CatalyticActivityCommentBuilderTest {

    @Test
    void canSetReaction() {
        Reaction r = new ReactionBuilder().build();
        CatalyticActivityComment obj = new CatalyticActivityCommentBuilder().reaction(r).build();
        assertTrue(obj.hasReaction());
    }

    @Test
    void canAddSinglePhysiologicalReaction() {
        CatalyticActivityComment obj =
                new CatalyticActivityCommentBuilder()
                        .addPhysiologicalReaction(createPhyReaction())
                        .build();
        assertNotNull(obj.getPhysiologicalReactions());
        assertFalse(obj.getPhysiologicalReactions().isEmpty());
        assertTrue(obj.hasPhysiologicalReactions());
    }

    @Test
    void nullPhysiologicalReaction_willBeIgnore() {
        CatalyticActivityComment obj =
                new CatalyticActivityCommentBuilder().addPhysiologicalReaction(null).build();
        assertNotNull(obj.getPhysiologicalReactions());
        assertTrue(obj.getPhysiologicalReactions().isEmpty());
        assertFalse(obj.hasPhysiologicalReactions());
    }

    @Test
    void physiologicalReaction_willConvertUnModifiable_toModifiable() {
        CatalyticActivityComment obj =
                new CatalyticActivityCommentBuilder()
                        .physiologicalReactions(Collections.emptyList())
                        .addPhysiologicalReaction(createPhyReaction())
                        .build();
        assertNotNull(obj.getPhysiologicalReactions());
        assertFalse(obj.getPhysiologicalReactions().isEmpty());
        assertTrue(obj.hasPhysiologicalReactions());
    }

    @Test
    void canAddListPhysiologicalReaction() {
        CatalyticActivityComment obj =
                new CatalyticActivityCommentBuilder()
                        .physiologicalReactions(createPhyReactions())
                        .build();
        assertNotNull(obj.getPhysiologicalReactions());
        assertFalse(obj.getPhysiologicalReactions().isEmpty());
        assertTrue(obj.hasPhysiologicalReactions());
    }

    @Test
    void canCreateBuilderFromInstance() {
        CatalyticActivityComment obj = new CatalyticActivityCommentBuilder().build();
        CatalyticActivityCommentBuilder builder = new CatalyticActivityCommentBuilder().from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        CatalyticActivityComment obj = new CatalyticActivityCommentBuilder().build();
        CatalyticActivityComment obj2 = new CatalyticActivityCommentBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}