package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineConverter;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CC;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.Interaction;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.InteractionObject;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.InteractionComment;
import org.uniprot.core.uniprotkb.comment.Interactor;


class CcInteractionConverterTest {
    private final CcLineConverter converter = new CcLineConverter(null, null);

    @Test
    void testInteraction() {
        /*
        CC   -!- INTERACTION:
              CC       PRO_0000033156; PRO_0000000092 [P05067]: APP; NbExp=4; IntAct=EBI-20824092, EBI-821758;
              CC       D3ZAR1; PRO_0000017322 [P98158]: Lrp2; NbExp=3; IntAct=EBI-9250714, EBI-9251342;
              CC       P12345; P84198-1: VIM; Xeno; NbExp=4; IntAct=EBI-356498, EBI-457639;
           
        */
        CcLineObject ccLineO = new CcLineObject();
        CC cc1 = new CC();
        cc1.setTopic(CC.CCTopicEnum.INTERACTION);

        Interaction ia = new Interaction();
        cc1.setObject(ia);
        InteractionObject iao1 = new InteractionObject();
        iao1.setFirstInteractant("PRO_0000033156");
        iao1.setSecondInteractant("PRO_0000000092");
        iao1.setSecondInteractantParent("P05067");
        iao1.setGene("APP");
        iao1.setNbexp(4);
        iao1.setFirstId("EBI-20824092");
        iao1.setSecondId("EBI-821758");

        InteractionObject iao2 = new InteractionObject();
        iao2.setFirstInteractant("D3ZAR1");
        iao2.setSecondInteractant("PRO_0000017322");
        iao2.setSecondInteractantParent("P98158");
        iao2.setGene("Lrp2");
        iao2.setNbexp(3);
        iao2.setFirstId("EBI-9250714");
        iao2.setSecondId("EBI-9251342");

        InteractionObject iao3 = new InteractionObject();
        iao3.setFirstInteractant("P12345");
        iao3.setSecondInteractant("P84198-1");
        iao3.setXeno(true);
        iao3.setGene("VIM");
        iao3.setNbexp(4);
        iao3.setFirstId("EBI-356498");
        iao3.setSecondId("EBI-457639");
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
        List<org.uniprot.core.uniprotkb.comment.Interaction> interactions =
                icomment.getInteractions();
        assertEquals(3, interactions.size());
        org.uniprot.core.uniprotkb.comment.Interaction inter1 = interactions.get(0);
        org.uniprot.core.uniprotkb.comment.Interaction inter2 = interactions.get(1);
        org.uniprot.core.uniprotkb.comment.Interaction inter3 = interactions.get(2);
        Interactor interactor11= inter1.getFirstInteractor();
        Interactor interactor12= inter1.getSecondInteractor();
        Interactor interactor21= inter2.getFirstInteractor();
        Interactor interactor22= inter2.getSecondInteractor();
        Interactor interactor31= inter3.getFirstInteractor();
        Interactor interactor32= inter3.getSecondInteractor();
        assertEquals(4, inter1.getNumberOfExperiments());
        assertFalse(inter1.isXeno());
        verifyInteractor(interactor11, "PRO_0000033156", null, null, "EBI-20824092");
        verifyInteractor(interactor12, "PRO_0000000092", "P05067", "APP", "EBI-821758");
        
        assertEquals(3, inter2.getNumberOfExperiments());
        assertFalse(inter2.isXeno());
        verifyInteractor(interactor21, null, "D3ZAR1", null, "EBI-9250714");
        verifyInteractor(interactor22, "PRO_0000017322", "P98158", "Lrp2", "EBI-9251342");
        
        
        assertEquals(4, inter3.getNumberOfExperiments());
        assertTrue(inter3.isXeno());
        verifyInteractor(interactor31, null, "P12345", null, "EBI-356498");
        verifyInteractor(interactor32, null, "P84198-1", "VIM", "EBI-457639");
        

    }
    private void verifyInteractor(Interactor interactor, String chainId, String acc, String gene, String intActId) {
    	assertEquals(chainId, interactor.getChainId());
    	if(acc ==null) {
    		assertNull(interactor.getUniProtkbAccession());
    	}else {
    		assertEquals(acc, interactor.getUniProtkbAccession().getValue());
    	}
    	assertEquals(gene, interactor.getGeneName());
    	assertEquals(intActId, interactor.getIntActId());
    }
}
