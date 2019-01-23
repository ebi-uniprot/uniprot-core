package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.InteractionBuilder;

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
        TestHelper.verifyJson(comment);
    }
}
