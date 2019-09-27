package org.uniprot.core.xml.uniprot.citation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.Unpublished;
import org.uniprot.core.citation.builder.UnpublishedBuilder;
import org.uniprot.core.xml.jaxb.uniprot.CitationType;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

class UnpublishedConverterTest {

    @Test
    void test() {
        Unpublished citation = create();
        UnpublishedConverter converter = new UnpublishedConverter();
        CitationType xmlCitation = converter.toXml(citation);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlCitation, CitationType.class, "citation"));
        Unpublished converted = converter.fromXml(xmlCitation);
        assertEquals(citation, converted);
    }

    @Test
    void testCitationConverter() {

        Citation citation = create();
        CitationConverter converter = new CitationConverter();
        CitationType xmlCitation = converter.toXml(citation);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlCitation, CitationType.class, "citation"));
        Citation converted = converter.fromXml(xmlCitation);
        assertEquals(citation, converted);
    }

    private Unpublished create() {
        UnpublishedBuilder builder = new UnpublishedBuilder();
        String title = "Some title";
        builder.title(title).publicationDate("MAY-2005");
        return builder.build();
    }
}
