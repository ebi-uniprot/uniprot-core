package uk.ac.ebi.uniprot.xmlparser.uniprot.citation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.Unpublished;
import uk.ac.ebi.uniprot.domain.citation.builder.UnpublishedBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CitationType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

class UnpublishedConverterTest {

	@Test
	void test() {
		Unpublished citation = create();
		UnpublishedConverter converter = new UnpublishedConverter();
		CitationType xmlCitation = converter.toXml(citation);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlCitation, CitationType.class, "citation"));
		Unpublished converted = converter.fromXml(xmlCitation);
		assertEquals(citation, converted);
	}
	@Test
	void testCitationConverter() {
	
		Citation citation = create();
		CitationConverter converter = new CitationConverter();
		CitationType xmlCitation = converter.toXml(citation);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlCitation, CitationType.class, "citation"));
		Citation converted = converter.fromXml(xmlCitation);
		assertEquals(citation, converted);
	}
	
	private Unpublished create() {
		UnpublishedBuilder builder = UnpublishedBuilder.newInstance();
		String title = "Some title";
		builder.title(title).publicationDate(UnpublishedBuilder.createPublicationDate("MAY-2005"));
		return builder.build();
	}
}
