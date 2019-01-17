package uk.ac.ebi.uniprot.xmlparser.uniprot.citation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.Patent;
import uk.ac.ebi.uniprot.domain.citation.builder.AbstractCitationBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.PatentBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CitationType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

class PatentConverterTest {

	@Test
	void test() {
		Patent citation = create();
        PatentConverter converter = new PatentConverter();
        CitationType xmlCitation = converter.toXml(citation);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlCitation, CitationType.class, "citation"));
		Patent converted = converter.fromXml(xmlCitation);
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
	
	private Patent create() {
		PatentBuilder builder = PatentBuilder.newInstance();
        String pnumber = "Some Number";
        String date ="2018";
        builder.patentNumber(pnumber)
        .publicationDate(AbstractCitationBuilder.createPublicationDate(date));
    
        return builder.build();
	}
}
