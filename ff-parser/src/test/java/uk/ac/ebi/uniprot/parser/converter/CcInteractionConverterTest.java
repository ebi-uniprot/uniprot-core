package uk.ac.ebi.uniprot.parser.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionType;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineConverter;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject;

public class CcInteractionConverterTest {
	private final CcLineConverter converter = new CcLineConverter();
	@Test
	public void testInteraction(){
		/*
		 CC   -!- INTERACTION:
         CC       Q9W1K5-1:CG11299; NbExp=1; IntAct=EBI-133844, EBI-212772;
         CC       Self; NbExp=1; IntAct=EBI-123485, EBI-123485;
         CC       Q8C1S0:2410018M14Rik (xeno); NbExp=1; IntAct=EBI-394562, EBI-398761;
        CC       localization (By similarity).
		 */
		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.INTERACTION;
		
		CcLineObject.Interaction ia = new CcLineObject.Interaction();
		cc1.object =ia;
		CcLineObject.InteractionObject iao1 = new CcLineObject.InteractionObject();
		iao1.spAc = "Q9W1K5-1";
		iao1.gene = "CG11299";
		iao1.nbexp = 1;
		iao1.firstId = "EBI-133844";
		iao1.secondId ="EBI-212772";
		
		CcLineObject.InteractionObject iao2 = new CcLineObject.InteractionObject();
		iao2.isSelf =true;
		
		iao2.nbexp = 1;
		iao2.firstId = "EBI-123485";
		iao2.secondId ="EBI-123484";
		
		CcLineObject.InteractionObject iao3 = new CcLineObject.InteractionObject();
		iao3.spAc = "Q8C1S0";
		iao3.gene = "CG112992";
		iao3.nbexp = 1;
		iao3.firstId = "EBI-133844";
		iao3.secondId ="EBI-212775";
		iao3.xeno =true;
		
		ia.interactions.add(iao1);
		ia.interactions.add(iao2);
		ia.interactions.add(iao3);
		ccLineO.ccs.add(cc1);
		List<Comment> comments = converter.convert(ccLineO) ;
		assertEquals(1, comments.size());
		Comment comment1 = comments.get(0);
		assertEquals(CommentType.INTERACTION, comment1.getCommentType());
		assertTrue (comment1 instanceof InteractionComment);
		InteractionComment icomment = (InteractionComment) comment1;
		List<Interaction> interactions =icomment.getInteractions();
		assertEquals(3, interactions.size());
		Interaction inter1 = interactions.get(0);
		Interaction inter2 = interactions.get(1);
		Interaction inter3 = interactions.get(2);
		assertEquals("EBI-133844", inter1.getFirstInteractor().getValue());
		assertEquals("EBI-212772", inter1.getSecondInteractor().getValue());
		assertEquals("Q9W1K5-1", inter1.getUniProtAccession().getValue());
		assertEquals("CG11299",inter1.getGeneName());
		assertEquals(1,inter1.getNumberOfExperiments());
		assertEquals(InteractionType.BINARY ,inter1.getType());
		assertEquals("EBI-123485", inter2.getFirstInteractor().getValue());
		assertEquals("EBI-123484", inter2.getSecondInteractor().getValue());
		assertEquals(InteractionType.SELF ,inter2.getType());
		assertEquals(1,inter1.getNumberOfExperiments());
		
		assertEquals("EBI-133844", inter3.getFirstInteractor().getValue());
		assertEquals("EBI-212775", inter3.getSecondInteractor().getValue());
		assertEquals("Q8C1S0", inter3.getUniProtAccession().getValue());
		assertEquals("CG112992",inter3.getGeneName());
		assertEquals(1,inter3.getNumberOfExperiments());
		assertEquals(InteractionType.XENO ,inter3.getType());
		
	}
	
}
