package org.uniprot.core.flatfile.parser.converter;

import org.junit.Test;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineConverter;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineObject;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.WebResourceComment;

import java.util.List;

import static org.junit.Assert.*;

public class CCWebResourceConverterTest {
	private final CcLineConverter converter = new CcLineConverter(null, null);
	
	@Test
	public void testWebResource(){
		//CC   -!- WEB RESOURCE: Name=CD40Lbase; Note=CD40L defect database;
        //CC       URL="http://bioinf.uta.fi/CD40Lbase/";
		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.WEB_RESOURCE;
		CcLineObject.WebResource wr =new CcLineObject.WebResource();
		wr.name ="CD40Lbase";
		wr.note ="CD40L defect database";
		wr.url ="http://bioinf.uta.fi/CD40Lbase/";
		cc1.object =wr;
		ccLineO.ccs.add(cc1);
	
		List<Comment> comments = converter.convert(ccLineO) ;
		assertEquals(1, comments.size());
		
		Comment comment1 =comments.get(0);
		assertEquals(CommentType.WEBRESOURCE, comment1.getCommentType());
		assertTrue (comment1 instanceof WebResourceComment);
		
		WebResourceComment wcomment = (WebResourceComment) comment1;
		assertEquals("CD40Lbase", wcomment.getResourceName());
		assertEquals("CD40L defect database", wcomment.getNote());
		assertEquals("http://bioinf.uta.fi/CD40Lbase/", wcomment.getResourceUrl());
		assertFalse(wcomment.isFtp());
		
	}
}
