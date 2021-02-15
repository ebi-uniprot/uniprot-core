package org.uniprot.core.xml.uniprot.citation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.ElectronicArticle;
import org.uniprot.core.citation.impl.ElectronicArticleBuilder;
import org.uniprot.core.xml.jaxb.uniprot.CitationType;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

@Slf4j
class ElectronicArticleConverterTest {

    @Test
    void test() {
        ElectronicArticle citation = create();
        ElectronicArticleConverter converter = new ElectronicArticleConverter();
        CitationType xmlCitation = converter.toXml(citation);
        log.debug(
                UniProtXmlTestHelper.toXmlString(xmlCitation, CitationType.class, "citation"));
        ElectronicArticle converted = converter.fromXml(xmlCitation);
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

    private ElectronicArticle create() {
        ElectronicArticleBuilder builder = new ElectronicArticleBuilder();
        String title = "Protein research";
        String date = "JAN-2018";

        String journalName = "Nature";
        builder.journalName(journalName)
                .locator("Some locator")
                .title(title)
                .authorsAdd("Sulson J.E.")
                .authorsAdd("JWaterston R.")
                .publicationDate(date);

        return builder.build();
    }
}
