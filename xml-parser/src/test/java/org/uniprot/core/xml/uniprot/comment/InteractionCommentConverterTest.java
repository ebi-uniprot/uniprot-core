package org.uniprot.core.xml.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.comment.InteractionComment;
import org.uniprot.core.uniprotkb.comment.InteractionType;
import org.uniprot.core.uniprotkb.comment.impl.InteractionBuilder;
import org.uniprot.core.uniprotkb.comment.impl.InteractionCommentBuilder;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;

class InteractionCommentConverterTest {

    @Test
    void test() {
        InteractionBuilder builder = new InteractionBuilder();
        Interaction interaction1 =
                builder.interactionType(InteractionType.SELF)
                        .numberOfExperiments(4)
                        .firstInteractor("EBI-749763")
                        .secondInteractor("EBI-749763")
                        .build();

        InteractionBuilder builder2 = new InteractionBuilder();
        Interaction interaction2 =
                builder2.interactionType(InteractionType.BINARY)
                        .geneName("GUCD1")
                        .numberOfExperiments(3)
                        .firstInteractor("EBI-749763")
                        .secondInteractor("EBI-8293751")
                        .uniProtAccession("Q96NT3")
                        .build();
        List<Interaction> interactions = new ArrayList<>();
        interactions.add(interaction1);
        interactions.add(interaction2);
        InteractionCommentBuilder commentBuilder = new InteractionCommentBuilder();
        InteractionComment comment = commentBuilder.interactionsSet(interactions).build();

        InteractionCommentConverter converter = new InteractionCommentConverter();
        List<CommentType> xmlComments = converter.toXml(comment);
        InteractionComment converted = converter.fromXml(xmlComments);
        assertEquals(comment, converted);
    }
}
