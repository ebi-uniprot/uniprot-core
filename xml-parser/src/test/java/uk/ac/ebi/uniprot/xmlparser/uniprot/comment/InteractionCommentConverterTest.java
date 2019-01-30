package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.InteractionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.InteractionCommentBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InteractionCommentConverterTest {

    @Test
    void test() {
        InteractionBuilder builder = new InteractionBuilder();
        Interaction interaction1 = builder.interactionType(InteractionType.SELF)

                .numberOfExperiments(4)
                .firstInteractor("EBI-749763")
                .secondInteractor("EBI-749763")
                .build();

        InteractionBuilder builder2 = new InteractionBuilder();
        Interaction interaction2 = builder2.interactionType(InteractionType.BINARY)
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
        InteractionComment comment =
                commentBuilder.interactions(interactions)
                        .build();

        InteractionCommentConverter converter = new InteractionCommentConverter();
        List<CommentType> xmlComments = converter.toXml(comment);
        InteractionComment converted = converter.fromXml(xmlComments);
        assertEquals(comment, converted);
    }

}
