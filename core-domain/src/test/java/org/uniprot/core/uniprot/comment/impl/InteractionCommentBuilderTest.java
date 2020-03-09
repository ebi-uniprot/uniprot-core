package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.InteractionComment;

class InteractionCommentBuilderTest {

    @Test
    void canAddSingleInteraction() {
        InteractionComment obj =
                new InteractionCommentBuilder()
                        .interactionsAdd(new InteractionBuilder().build())
                        .build();
        assertNotNull(obj.getInteractions());
        assertFalse(obj.getInteractions().isEmpty());
        assertTrue(obj.hasInteractions());
    }

    @Test
    void nullInteraction_willBeIgnore() {
        InteractionComment obj = new InteractionCommentBuilder().interactionsAdd(null).build();
        assertNotNull(obj.getInteractions());
        assertTrue(obj.getInteractions().isEmpty());
        assertFalse(obj.hasInteractions());
    }

    @Test
    void canCreateBuilderFromInstance() {
        InteractionComment obj = new InteractionCommentBuilder().build();
        InteractionCommentBuilder builder = InteractionCommentBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        InteractionComment obj = new InteractionCommentBuilder().build();
        InteractionComment obj2 = new InteractionCommentBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
