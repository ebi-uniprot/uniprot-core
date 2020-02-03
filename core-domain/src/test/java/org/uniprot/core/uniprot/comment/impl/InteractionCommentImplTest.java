package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.Interaction;
import org.uniprot.core.uniprot.comment.InteractionComment;
import org.uniprot.core.uniprot.comment.InteractionType;
import org.uniprot.core.uniprot.comment.builder.InteractionBuilder;
import org.uniprot.core.uniprot.comment.builder.InteractionCommentBuilder;

class InteractionCommentImplTest {
    private List<Interaction> interactions =
            Arrays.asList(
                    new InteractionBuilder()
                            .interactionType(InteractionType.BINARY)
                            .geneName("gn22")
                            .numberOfExperiments(3)
                            .firstInteractor("first1")
                            .secondInteractor("first2")
                            .uniProtAccession("P12345")
                            .build(),
                    new InteractionBuilder()
                            .interactionType(InteractionType.SELF)
                            .geneName("some gene name")
                            .numberOfExperiments(12)
                            .firstInteractor("first3")
                            .secondInteractor("first4")
                            .build());

    @Test
    void testInteractionCommentImpl() {
        InteractionComment comment = new InteractionCommentImpl(interactions);
        assertEquals(interactions, comment.getInteractions());
        assertEquals(CommentType.INTERACTION, comment.getCommentType());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        InteractionComment obj = new InteractionCommentImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        InteractionComment impl = new InteractionCommentImpl(interactions);
        InteractionComment obj = InteractionCommentBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
