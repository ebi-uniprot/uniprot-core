package uk.ac.ebi.uniprot.xmlparser.uniprot.citation;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.Patent;
import uk.ac.ebi.uniprot.domain.citation.builder.PatentBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CitationType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        PatentBuilder builder = new PatentBuilder();
        String pnumber = "Some Number";
        String date = "2018";
        builder.patentNumber(pnumber)
                .publicationDate(date);

        return builder.build();
    }
}
