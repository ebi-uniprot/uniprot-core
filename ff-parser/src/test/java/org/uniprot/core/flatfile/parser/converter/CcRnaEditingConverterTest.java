package org.uniprot.core.flatfile.parser.converter;

import org.junit.Test;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineConverter;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineObject.EvidencedString;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.RnaEditingComment;
import org.uniprot.core.uniprot.comment.RnaEditingLocationType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CcRnaEditingConverterTest {
	private final CcLineConverter converter = new CcLineConverter(null, null);
	@Test
	public void testRNAEditing(){
		//CC   -!- RNA EDITING: Modified_positions=1, 56, 89, 103, 126, 164;
        //CC       Note=The initiator methionine is created by RNA editing.
		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.RNA_EDITING;
		CcLineObject.RnaEditing rnaEd =new CcLineObject.RnaEditing();
		rnaEd.note.add(new EvidencedString(
				"The initiator methionine is created by RNA editing.", new ArrayList<String>()));
		rnaEd.locations.add(1);
		rnaEd.locations.add(56);
		rnaEd.locations.add(89);
		rnaEd.locations.add(103);
		rnaEd.locations.add(126);
		rnaEd.locations.add(164);
	
		cc1.object =rnaEd;
		ccLineO.ccs.add(cc1);
	
		List<Comment> comments = converter.convert(ccLineO) ;
		assertEquals(1, comments.size());
		
		Comment comment1 =comments.get(0);
		assertEquals(CommentType.RNA_EDITING, comment1.getCommentType());
		assertTrue (comment1 instanceof RnaEditingComment);
		
		RnaEditingComment wcomment = (RnaEditingComment) comment1;
		
		assertEquals("The initiator methionine is created by RNA editing",
				wcomment.getNote().getTexts().get(0).getValue());
		assertEquals(RnaEditingLocationType.Known, wcomment.getLocationType());
		assertEquals(6, wcomment.getPositions().size());
		assertEquals("1", wcomment.getPositions().get(0).getPosition());
		assertEquals("126", wcomment.getPositions().get(4).getPosition());
		
	}
}