package org.uniprot.core.xml.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.Interaction;
import org.uniprot.core.uniprot.comment.InteractionType;
import org.uniprot.core.uniprot.comment.builder.InteractionBuilder;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

class InteractionConverterTest {

    @Test
    void testBinary() {
        // Q96NT3:GUCD1; NbExp=3; IntAct=EBI-749763, EBI-8293751;
        InteractionBuilder builder = new InteractionBuilder();
        Interaction interaction =
                builder.interactionType(InteractionType.BINARY)
                        .geneName("GUCD1")
                        .numberOfExperiments(3)
                        .firstInteractor("EBI-749763")
                        .secondInteractor("EBI-8293751")
                        .uniProtAccession("Q96NT3")
                        .build();
        InteractionConverter converter = new InteractionConverter();
        CommentType xmlComment = converter.toXml(interaction);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
        Interaction converted = converter.fromXml(xmlComment);
        assertEquals(interaction, converted);
    }

    @Test
    void testSelf() {
        // Self; NbExp=4; IntAct=EBI-749763, EBI-749763;
        InteractionBuilder builder = new InteractionBuilder();
        Interaction interaction =
                builder.interactionType(InteractionType.SELF)
                        .numberOfExperiments(4)
                        .firstInteractor("EBI-749763")
                        .secondInteractor("EBI-749763")
                        .build();
        InteractionConverter converter = new InteractionConverter();
        CommentType xmlComment = converter.toXml(interaction);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
        Interaction converted = converter.fromXml(xmlComment);
        assertEquals(interaction, converted);
    }

    @Test
    void testXENO() {
        // P05845:tnsE (xeno); NbExp=4; IntAct=EBI-542385, EBI-2434514;
        InteractionBuilder builder = new InteractionBuilder();
        Interaction interaction =
                builder.interactionType(InteractionType.XENO)
                        .geneName("tnsE")
                        .numberOfExperiments(4)
                        .firstInteractor("EBI-542385")
                        .secondInteractor("EBI-2434514")
                        .uniProtAccession("P05845")
                        .build();
        InteractionConverter converter = new InteractionConverter();
        CommentType xmlComment = converter.toXml(interaction);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
        Interaction converted = converter.fromXml(xmlComment);
        assertEquals(interaction, converted);
    }
}
