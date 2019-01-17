package uk.ac.ebi.uniprot.xmlparser.uniprot.citation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.Submission;
import uk.ac.ebi.uniprot.domain.citation.SubmissionDatabase;
import uk.ac.ebi.uniprot.domain.citation.builder.AbstractCitationBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.SubmissionBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CitationType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

class SubmissionConverterTest {

	@Test
	void test() {
		Submission citation = create();
		SubmissionConverter converter = new SubmissionConverter();
		CitationType xmlCitation = converter.toXml(citation);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlCitation, CitationType.class, "citation"));
		Submission converted = converter.fromXml(xmlCitation);
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
	
	private Submission create() {
		SubmissionBuilder builder = SubmissionBuilder.newInstance();
		String title = "Protein research";
		String date = "JAN-2018";
		builder.submittedToDatabase(SubmissionDatabase.EMBL_GENBANK_DDBJ).title(title)
				.authors(Arrays.asList(AbstractCitationBuilder.createAuthor("Sulson J.E."),
						AbstractCitationBuilder.createAuthor("JWaterston R.")))
				.publicationDate(AbstractCitationBuilder.createPublicationDate(date));
		return builder.build();
	}
}
