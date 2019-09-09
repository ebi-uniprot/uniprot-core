package org.uniprot.core.flatfile.parser.converter;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineConverter;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineObject.AlternativeNameSequenceEnum;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineObject.EvidencedString;
import org.uniprot.core.uniprot.comment.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CcAPConverterTest {
	private final CcLineConverter converter = new CcLineConverter(null, null);
	@Test
	public void testAlternatProduct(){
		//CC   -!- ALTERNATIVE PRODUCTS:
        //CC       Event=Alternative splicing; Named isoforms=3;
        //CC         Comment=Additional isoforms seem to exist. Experimental
        //CC         confirmation may be lacking for some isoforms;
        //CC       Name=1; Synonyms=AIRE-1;
        //CC         IsoId=O43918-1; Sequence=Displayed;
		//CC       Name=2; Synonyms=AIRE-2;
        //CC         IsoId=O43918-2; Sequence=VSP_004089;
		 //CC         Note=Major isoform found in 66-78% of cDNA clones
        //CC       Name=3; Synonyms=AIRE-3;
       //CC         IsoId=O43918-3; Sequence=VSP_004089, VSP_004090;
		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.ALTERNATIVE_PRODUCTS;
		CcLineObject.AlternativeProducts wr =
				new CcLineObject.AlternativeProducts();
		 String commentStr ="Additional isoforms seem to exist. "
			+ "Experimental confirmation may be lacking for some isoforms";
		wr.comment.add(new EvidencedString(commentStr, new ArrayList<String>()));
		wr.namedIsoforms ="3";
		wr.events.add("Alternative splicing");
		CcLineObject.AlternativeProductName alName1 = new 
				CcLineObject.AlternativeProductName();
		alName1.name =new EvidencedString("1", new ArrayList<String>());
		alName1.synNames.add(new EvidencedString("AIRE-1", new ArrayList<String>()));
		alName1.isoId.add("O43918-1");
		alName1.sequenceEnum = AlternativeNameSequenceEnum.DISPLAYED;
		
		CcLineObject.AlternativeProductName alName2 = new 
				CcLineObject.AlternativeProductName();
		alName2.name =new EvidencedString("2", new ArrayList<String>());
		alName2.synNames.add(new EvidencedString("AIRE-2", new ArrayList<String>()));
		alName2.isoId.add("O43918-2");
		String alNameNote ="Major isoform found in 66-78% of cDNA clones";
		alName2.note.add(new EvidencedString(alNameNote, new ArrayList<String>()));
		alName2.sequenceFTId.add("VSP_004089");
		
		CcLineObject.AlternativeProductName alName3 = new 
				CcLineObject.AlternativeProductName();
		alName3.name =new EvidencedString("3", new ArrayList<String>());
		alName3.synNames.add(new EvidencedString("AIRE-3", new ArrayList<String>()));
		alName3.isoId.add("O43918-3");
		alName3.sequenceFTId.add("VSP_004089");
		alName3.sequenceFTId.add("VSP_004090");
		
		wr.names.add(alName1);
		wr.names.add(alName2);
		wr.names.add(alName3);
		cc1.object =wr;
		ccLineO.ccs.add(cc1);
	
		List<Comment> comments = converter.convert(ccLineO) ;
		assertEquals(1, comments.size());
		
		Comment comment1 =comments.get(0);
		assertEquals(CommentType.ALTERNATIVE_PRODUCTS, comment1.getCommentType());
		assertTrue (comment1 instanceof AlternativeProductsComment);
		
		AlternativeProductsComment wcomment 
		= (AlternativeProductsComment) comment1;
		List<APEventType> events =wcomment.getEvents();
		List<APIsoform> isoforms =wcomment.getIsoforms();
		assertEquals(1, events.size());
		APEventType event = events.get(0);
		assertEquals("Alternative splicing", event.getName());
		assertEquals("Additional isoforms seem to exist. "
				+ "Experimental confirmation may be lacking for some isoforms", 
				wcomment.getNote().getTexts().get(0).getValue());
		assertEquals(3, isoforms.size());
		APIsoform isoform1 =isoforms.get(0);
		APIsoform isoform2 =isoforms.get(1);
		APIsoform isoform3 =isoforms.get(2);
		assertEquals("1", isoform1.getName().getValue());
		List<IsoformName> syns1 =isoform1.getSynonyms();
		assertEquals(1, syns1.size());
		assertEquals(0, isoform1.getSequenceIds().size());
		assertEquals("AIRE-1", syns1.get(0).getValue());
		assertEquals(IsoformSequenceStatus.DISPLAYED, isoform1.getIsoformSequenceStatus());
		assertFalse(isoform1.getNote() !=null);
		List<IsoformId> ids1 = isoform1.getIsoformIds();
		assertEquals(1, ids1.size());
		assertEquals("O43918-1", ids1.get(0).getValue());
		
		assertEquals("2", isoform2.getName().getValue());
		List<IsoformName> syns2 =isoform2.getSynonyms();
		assertEquals(1, syns2.size());
		assertEquals("AIRE-2", syns2.get(0).getValue());
		List<String> seqIds2 =isoform2.getSequenceIds();
		assertEquals(1, seqIds2.size());
		assertEquals("VSP_004089", seqIds2.get(0));
	//	assertEquals(IsoformSequenceStatus.DISPLAYED, isoform2.getIsoformSequenceStatus());
		assertTrue(isoform2.getNote() !=null);
		assertEquals("Major isoform found in 66-78% of cDNA clones", 
				isoform2.getNote().getTexts().get(0).getValue());
		List<IsoformId> ids2 = isoform2.getIsoformIds();
		assertEquals(1, ids2.size());
		assertEquals("O43918-2", ids2.get(0).getValue());
		
		assertEquals("3", isoform3.getName().getValue());
		List<IsoformName> syns3 =isoform3.getSynonyms();
		assertEquals(1, syns3.size());
		assertEquals("AIRE-3", syns3.get(0).getValue());
		List<String> seqIds3 =isoform3.getSequenceIds();
		assertEquals(2, seqIds3.size());
		assertEquals("VSP_004089", seqIds3.get(0));
		assertEquals("VSP_004090", seqIds3.get(1));
	//	assertEquals(IsoformSequenceStatus.DISPLAYED, isoform2.getIsoformSequenceStatus());
		assertFalse(isoform3.getNote() !=null);
		List<IsoformId> ids3 = isoform3.getIsoformIds();
		assertEquals(1, ids3.size());
		assertEquals("O43918-3", ids3.get(0).getValue());
	}
	
}
