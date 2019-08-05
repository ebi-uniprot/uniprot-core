package uk.ac.ebi.uniprot.xml.uniprot.citation;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.Thesis;
import org.uniprot.core.citation.builder.ThesisBuilder;

import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CitationType;
import uk.ac.ebi.uniprot.xml.uniprot.UniProtXmlTestHelper;
import uk.ac.ebi.uniprot.xml.uniprot.citation.CitationConverter;
import uk.ac.ebi.uniprot.xml.uniprot.citation.ThesisConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        ThesisBuilder builder = new ThesisBuilder();
        String institute = "Cambridge University";
        String address = "United Kingdom";
        builder.institute(institute);
        builder.address(address);
        String title = "Protein research";
        String date = "JAN-2018";
        builder.title(title)
                .addAuthor("Sulson J.E.")
                .addAuthor("JWaterston R.")
                .publicationDate(date);
        return builder.build();
    }
}
