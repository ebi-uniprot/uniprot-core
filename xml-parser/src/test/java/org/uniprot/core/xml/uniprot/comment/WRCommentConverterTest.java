package org.uniprot.core.xml.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.WebResourceComment;
import org.uniprot.core.uniprotkb.comment.impl.WebResourceCommentBuilder;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

@Slf4j
class WRCommentConverterTest {

    @Test
    void testWithNote() {
        WebResourceCommentBuilder builder = new WebResourceCommentBuilder();
        WebResourceComment comment =
                builder.resourceName("Mutations of the PDE6A/B/G genes")
                        .resourceUrl(
                                "http://www.retina-international.org/files/sci-news/pdemut.htm")
                        .note("Retina International's Scientific Newsletter")
                        .build();
        WRCommentConverter converter = new WRCommentConverter();
        CommentType xmlComment = converter.toXml(comment);
        log.debug(
                UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
        WebResourceComment converted = converter.fromXml(xmlComment);
        assertEquals(comment, converted);
    }

    @Test
    void testWithoutNote() {
        WebResourceCommentBuilder builder = new WebResourceCommentBuilder();
        WebResourceComment comment =
                builder.resourceName("Wikipedia")
                        .resourceUrl("https://en.wikipedia.org/wiki/CXCR4")
                        .build();
        WRCommentConverter converter = new WRCommentConverter();
        CommentType xmlComment = converter.toXml(comment);
        log.debug(
                UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
        WebResourceComment converted = converter.fromXml(xmlComment);
        assertEquals(comment, converted);
    }
}
