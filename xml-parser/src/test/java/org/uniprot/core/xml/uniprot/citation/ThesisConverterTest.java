package org.uniprot.core.xml.uniprot.citation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.Thesis;
import org.uniprot.core.citation.impl.ThesisBuilder;
import org.uniprot.core.xml.jaxb.uniprot.CitationType;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

@Slf4j
class ThesisConverterTest {

    @Test
    void test() {
        Thesis citation = create();
        ThesisConverter converter = new ThesisConverter();
        CitationType xmlCitation = converter.toXml(citation);
        log.debug(
                UniProtXmlTestHelper.toXmlString(xmlCitation, CitationType.class, "citation"));
        Thesis converted = converter.fromXml(xmlCitation);
        assertEquals(citation, converted);
    }

    @Test
    void testCitationConverter() {

        Citation citation = create();
        CitationConverter converter = new CitationConverter();
        CitationType xmlCitation = converter.toXml(citation);
        log.debug(
                UniProtXmlTestHelper.toXmlString(xmlCitation, CitationType.class, "citation"));
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
                .authorsAdd("Sulson J.E.")
                .authorsAdd("JWaterston R.")
                .publicationDate(date);
        return builder.build();
    }
}
