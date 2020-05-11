package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createPhyReaction;
import static org.uniprot.core.ObjectsForTests.createPhyReactions;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.CatalyticActivityComment;
import org.uniprot.core.uniprotkb.comment.Reaction;

import java.util.Collections;

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
                        .physiologicalReactionsAdd(createPhyReaction())
                        .build();
        assertNotNull(obj.getPhysiologicalReactions());
        assertFalse(obj.getPhysiologicalReactions().isEmpty());
        assertTrue(obj.hasPhysiologicalReactions());
    }

    @Test
    void nullPhysiologicalReaction_willBeIgnore() {
        CatalyticActivityComment obj =
                new CatalyticActivityCommentBuilder().physiologicalReactionsAdd(null).build();
        assertNotNull(obj.getPhysiologicalReactions());
        assertTrue(obj.getPhysiologicalReactions().isEmpty());
        assertFalse(obj.hasPhysiologicalReactions());
    }

    @Test
    void physiologicalReaction_willConvertUnModifiable_toModifiable() {
        CatalyticActivityComment obj =
                new CatalyticActivityCommentBuilder()
                        .physiologicalReactionsSet(Collections.emptyList())
                        .physiologicalReactionsAdd(createPhyReaction())
                        .build();
        assertNotNull(obj.getPhysiologicalReactions());
        assertFalse(obj.getPhysiologicalReactions().isEmpty());
        assertTrue(obj.hasPhysiologicalReactions());
    }

    @Test
    void canAddListPhysiologicalReaction() {
        CatalyticActivityComment obj =
                new CatalyticActivityCommentBuilder()
                        .physiologicalReactionsSet(createPhyReactions())
                        .build();
        assertNotNull(obj.getPhysiologicalReactions());
        assertFalse(obj.getPhysiologicalReactions().isEmpty());
        assertTrue(obj.hasPhysiologicalReactions());
    }

    @Test
    void canCreateBuilderFromInstance() {
        CatalyticActivityComment obj = new CatalyticActivityCommentBuilder().build();
        CatalyticActivityCommentBuilder builder = CatalyticActivityCommentBuilder.from(obj);
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
