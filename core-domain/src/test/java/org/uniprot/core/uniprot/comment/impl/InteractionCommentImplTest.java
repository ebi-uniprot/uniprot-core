package org.uniprot.core.uniprot.comment.impl;

import org.junit.jupiter.api.Test;

import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.Interaction;
import org.uniprot.core.uniprot.comment.InteractionComment;
import org.uniprot.core.uniprot.comment.InteractionType;
import org.uniprot.core.uniprot.comment.builder.InteractionBuilder;
import org.uniprot.core.uniprot.comment.impl.InteractionCommentImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InteractionCommentImplTest {
    @Test
    void testInteractionCommentImpl() {
        List<Interaction> interactions = new ArrayList<>();
        interactions.add(new InteractionBuilder()
                                 .interactionType(InteractionType.BINARY)
                                 .geneName("gn22")
                                 .numberOfExperiments(3)
                                 .firstInteractor("first1")
                                 .secondInteractor("first2")
                                 .uniProtAccession("P12345")
                                 .build());
        interactions.add(new InteractionBuilder()
                                 .interactionType(InteractionType.SELF)
                                 .geneName("some gene name")
                                 .numberOfExperiments(12)
                                 .firstInteractor("first3")
                                 .secondInteractor("first4")
                                 .build());

        InteractionComment comment = new InteractionCommentImpl(interactions);
        assertEquals(interactions, comment.getInteractions());
        assertEquals(CommentType.INTERACTION, comment.getCommentType());
    }
}
