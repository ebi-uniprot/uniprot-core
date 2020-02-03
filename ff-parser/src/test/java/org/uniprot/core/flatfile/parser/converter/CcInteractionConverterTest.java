package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineConverter;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CC;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.Interaction;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.InteractionObject;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.InteractionComment;
import org.uniprot.core.uniprot.comment.InteractionType;

class CcInteractionConverterTest {
    private final CcLineConverter converter = new CcLineConverter(null, null);

    @Test
    void testInteraction() {
        /*
        CC   -!- INTERACTION:
              CC       Q9W1K5-1:CG11299; NbExp=1; IntAct=EBI-133844, EBI-212772;
              CC       Self; NbExp=1; IntAct=EBI-123485, EBI-123485;
              CC       Q8C1S0:2410018M14Rik (xeno); NbExp=1; IntAct=EBI-394562, EBI-398761;
             CC       localization (By similarity).
        */
        CcLineObject ccLineO = new CcLineObject();
        CC cc1 = new CC();
        cc1.setTopic(CC.CCTopicEnum.INTERACTION);

        Interaction ia = new Interaction();
        cc1.setObject(ia);
        InteractionObject iao1 = new InteractionObject();
        iao1.setSpAc("Q9W1K5-1");
        iao1.setGene("CG11299");
        iao1.setNbexp(1);
        iao1.setFirstId("EBI-133844");
        iao1.setSecondId("EBI-212772");

        InteractionObject iao2 = new InteractionObject();
        iao2.setSelf(true);

        iao2.setNbexp(1);
        iao2.setFirstId("EBI-123485");
        iao2.setSecondId("EBI-123484");

        InteractionObject iao3 = new InteractionObject();
        iao3.setSpAc("Q8C1S0");
        iao3.setGene("CG112992");
        iao3.setNbexp(1);
        iao3.setFirstId("EBI-133844");
        iao3.setSecondId("EBI-212775");
        iao3.setXeno(true);

        ia.getInteractions().add(iao1);
        ia.getInteractions().add(iao2);
        ia.getInteractions().add(iao3);
        ccLineO.getCcs().add(cc1);
        List<Comment> comments = converter.convert(ccLineO);
        assertEquals(1, comments.size());
        Comment comment1 = comments.get(0);
        assertEquals(CommentType.INTERACTION, comment1.getCommentType());
        assertTrue(comment1 instanceof InteractionComment);
        InteractionComment icomment = (InteractionComment) comment1;
        List<org.uniprot.core.uniprot.comment.Interaction> interactions =
                icomment.getInteractions();
        assertEquals(3, interactions.size());
        org.uniprot.core.uniprot.comment.Interaction inter1 = interactions.get(0);
        org.uniprot.core.uniprot.comment.Interaction inter2 = interactions.get(1);
        org.uniprot.core.uniprot.comment.Interaction inter3 = interactions.get(2);
        assertEquals("EBI-133844", inter1.getFirstInteractor().getValue());
        assertEquals("EBI-212772", inter1.getSecondInteractor().getValue());
        assertEquals("Q9W1K5-1", inter1.getUniProtAccession().getValue());
        assertEquals("CG11299", inter1.getGeneName());
        assertEquals(1, inter1.getNumberOfExperiments());
        assertEquals(InteractionType.BINARY, inter1.getType());
        assertEquals("EBI-123485", inter2.getFirstInteractor().getValue());
        assertEquals("EBI-123484", inter2.getSecondInteractor().getValue());
        assertEquals(InteractionType.SELF, inter2.getType());
        assertEquals(1, inter1.getNumberOfExperiments());

        assertEquals("EBI-133844", inter3.getFirstInteractor().getValue());
        assertEquals("EBI-212775", inter3.getSecondInteractor().getValue());
        assertEquals("Q8C1S0", inter3.getUniProtAccession().getValue());
        assertEquals("CG112992", inter3.getGeneName());
        assertEquals(1, inter3.getNumberOfExperiments());
        assertEquals(InteractionType.XENO, inter3.getType());
    }
}
