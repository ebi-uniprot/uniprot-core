package uk.ac.ebi.uniprot.xmlparser.uniprot.citation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.Thesis;
import uk.ac.ebi.uniprot.domain.citation.builder.AbstractCitationBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.ThesisBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CitationType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

class ThesisConverterTest {

	@Test
	void test() {
		Thesis citation = create();
		ThesisConverter converter = new ThesisConverter();
		CitationType xmlCitation = converter.toXml(citation);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlCitation, CitationType.class, "citation"));
		Thesis converted = converter.fromXml(xmlCitation);
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
	
	
	private Thesis create() {
		ThesisBuilder builder = ThesisBuilder.newInstance();
		String institute = "Cambridge University";
		String address = "United Kingdom";
		builder.institute(institute);
		builder.address(address);
		String title = "Protein research";
		String date = "JAN-2018";
		builder.title(title)
				.authors(Arrays.asList(AbstractCitationBuilder.createAuthor("Sulson J.E."),
						AbstractCitationBuilder.createAuthor("JWaterston R.")))
				.publicationDate(AbstractCitationBuilder.createPublicationDate(date));
		return builder.build();
	}
}
