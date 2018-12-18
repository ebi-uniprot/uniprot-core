package uk.ac.ebi.uniprot.parser.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.FreeTextComment;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineConverter;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject.EvidencedString;

public class CcFreeTextConverterTest {
	private final CcLineConverter converter = new CcLineConverter(null);
	@Test
	public void testTextOnly(){
		/*
		 * CC   -!- FUNCTION: This enzyme is necessary for target cell lysis in cell-
        CC       mediated immune responses. It cleaves after Lys or Arg. May be
        CC       involved in apoptosis.
        CC   -!- DOMAIN: The di-lysine motif may confer endoplasmic reticulum
        CC       localization (By similarity).
		 */
		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.FUNCTION;
		CcLineObject.FreeText texts = new CcLineObject.FreeText();
		String val ="This enzyme is necessary for target cell lysis in cell-mediated immune responses."
				+ " It cleaves after Lys or Arg. May be involved in apoptosis.";
		texts.texts.add(new EvidencedString(val, new ArrayList<String>()));
		cc1.object =texts;
		CcLineObject.CC cc2 =new CcLineObject.CC();
		cc2.topic =CcLineObject.CCTopicEnum.DOMAIN;
		CcLineObject.FreeText texts2 = new CcLineObject.FreeText();
		String val2 ="The di-lysine motif may confer endoplasmic reticulum localization (By similarity)";
		texts2.texts.add(new EvidencedString(val2, new ArrayList<String>()));
		cc2.object =texts2;
		ccLineO.ccs.add(cc1);
		ccLineO.ccs.add(cc2);
		List<Comment> comments = converter.convert(ccLineO) ;
		assertEquals(2, comments.size());
		
		Comment comment1 =comments.get(0);
		assertEquals(CommentType.FUNCTION, comment1.getCommentType());
		assertTrue (comment1 instanceof FreeTextComment);
		
		FreeTextComment fcomment = (FreeTextComment) comment1;
		assertEquals("This enzyme is necessary for target cell lysis in cell-mediated immune responses."
				+ " It cleaves after Lys or Arg. May be involved in apoptosis.",
				fcomment.getTexts().get(0).getValue());
	//	assertEquals(CommentStatus.EXPERIMENTAL, fcomment.getCommentStatus());
		Comment comment2 =comments.get(1);
		assertEquals(CommentType.DOMAIN, comment2.getCommentType());
		assertTrue (comment2 instanceof FreeTextComment);
		
		FreeTextComment dcomment = (FreeTextComment) comment2;
		assertEquals("The di-lysine motif may confer endoplasmic reticulum localization (By similarity)",
				dcomment.getTexts().get(0).getValue());
	}
	
}
