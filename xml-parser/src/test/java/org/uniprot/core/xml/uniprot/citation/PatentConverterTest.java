package org.uniprot.core.xml.uniprot.citation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.Patent;
import org.uniprot.core.citation.impl.PatentBuilder;
import org.uniprot.core.xml.jaxb.uniprot.CitationType;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

@Slf4j
class PatentConverterTest {

    @Test
    void test() {
        Patent citation = create();
        PatentConverter converter = new PatentConverter();
        CitationType xmlCitation = converter.toXml(citation);
        log.debug(
                UniProtXmlTestHelper.toXmlString(xmlCitation, CitationType.class, "citation"));
        Patent converted = converter.fromXml(xmlCitation);
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

    private Patent create() {
        PatentBuilder builder = new PatentBuilder();
        String pnumber = "Some Number";
        String date = "2018";
        builder.patentNumber(pnumber).publicationDate(date);

        return builder.build();
    }
}
