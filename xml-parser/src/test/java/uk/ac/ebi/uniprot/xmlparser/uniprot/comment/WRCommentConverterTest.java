package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.uniprot.comment.WebResourceComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.WebResourceCommentBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

class WRCommentConverterTest {

	@Test
	void testWithNote() {
		WebResourceCommentBuilder builder = WebResourceCommentBuilder.newInstance();
		WebResourceComment comment =
		builder.resourceName("Mutations of the PDE6A/B/G genes")
		.resourceUrl("http://www.retina-international.org/files/sci-news/pdemut.htm")
		.note("Retina International's Scientific Newsletter")
		.build();
		WRCommentConverter converter = new WRCommentConverter();
		CommentType xmlComment = converter.toXml(comment);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
		WebResourceComment converted = converter.fromXml(xmlComment);
		assertEquals(comment, converted);
		
	}
	@Test
	void testWithoutNote() {
		WebResourceCommentBuilder builder = WebResourceCommentBuilder.newInstance();
		WebResourceComment comment =
		builder.resourceName("Wikipedia")
		.resourceUrl("https://en.wikipedia.org/wiki/CXCR4")
		.build();
		WRCommentConverter converter = new WRCommentConverter();
		CommentType xmlComment = converter.toXml(comment);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
		WebResourceComment converted = converter.fromXml(xmlComment);
		assertEquals(comment, converted);
		
	}

}
