package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineConverter;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CC;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.WebResource;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.WebResourceComment;

class CCWebResourceConverterTest {
    private final CcLineConverter converter = new CcLineConverter(null, null);

    @Test
    void testWebResource() {
        // CC   -!- WEB RESOURCE: Name=CD40Lbase; Note=CD40L defect database;
        // CC       URL="http://bioinf.uta.fi/CD40Lbase/";
        CcLineObject ccLineO = new CcLineObject();
        CC cc1 = new CC();
        cc1.setTopic(CcLineObject.CCTopicEnum.WEB_RESOURCE);
        WebResource wr = new WebResource();
        wr.setName("CD40Lbase");
        wr.setNote("CD40L defect database");
        wr.setUrl("http://bioinf.uta.fi/CD40Lbase/");
        cc1.setObject(wr);
        ccLineO.getCcs().add(cc1);

        List<Comment> comments = converter.convert(ccLineO);
        assertEquals(1, comments.size());

        Comment comment1 = comments.get(0);
        assertEquals(CommentType.WEBRESOURCE, comment1.getCommentType());
        assertTrue(comment1 instanceof WebResourceComment);

        WebResourceComment wcomment = (WebResourceComment) comment1;
        assertEquals("CD40Lbase", wcomment.getResourceName());
        assertEquals("CD40L defect database", wcomment.getNote());
        assertEquals("http://bioinf.uta.fi/CD40Lbase/", wcomment.getResourceUrl());
        assertFalse(wcomment.isFtp());
    }
}
