package uk.ac.ebi.uniprot.xmlparser.uniprot.citation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.ElectronicArticle;
import uk.ac.ebi.uniprot.domain.citation.builder.AbstractCitationBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.ElectronicArticleBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CitationType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

class ElectronicArticleConverterTest {

	@Test
	void test() {
		ElectronicArticle citation = create();
		ElectronicArticleConverter converter = new ElectronicArticleConverter();
		CitationType xmlCitation = converter.toXml(citation);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlCitation, CitationType.class, "citation"));
		ElectronicArticle converted = converter.fromXml(xmlCitation);
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
	
	private ElectronicArticle create() {
		ElectronicArticleBuilder builder = ElectronicArticleBuilder.newInstance();
		String title = "Protein research";
		String date = "JAN-2018";

		String journalName = "Nature";
		builder.journalName(journalName).locator("Some locator").title(title)
				.authors(Arrays.asList(AbstractCitationBuilder.createAuthor("Sulson J.E."),
						AbstractCitationBuilder.createAuthor("JWaterston R.")))
				.publicationDate(AbstractCitationBuilder.createPublicationDate(date));

		ElectronicArticle citation = builder.build();
		return citation;
	}
}
