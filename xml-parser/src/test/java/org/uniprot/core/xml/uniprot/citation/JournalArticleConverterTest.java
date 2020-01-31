package org.uniprot.core.xml.uniprot.citation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationXrefType;
import org.uniprot.core.citation.JournalArticle;
import org.uniprot.core.citation.builder.JournalArticleBuilder;
import org.uniprot.core.impl.DBCrossReferenceImpl;
import org.uniprot.core.xml.jaxb.uniprot.CitationType;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

class JournalArticleConverterTest {

    @Test
    void test() {

        JournalArticle citation = create();
        JournalArticleConverter converter = new JournalArticleConverter();
        CitationType xmlCitation = converter.toXml(citation);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlCitation, CitationType.class, "citation"));
        JournalArticle converted = converter.fromXml(xmlCitation);
        assertEquals(citation, converted);
    }

    @Test
    void testCitationConverter() {

        JournalArticle citation = create();
        CitationConverter converter = new CitationConverter();
        CitationType xmlCitation = converter.toXml(citation);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlCitation, CitationType.class, "citation"));
        Citation converted = converter.fromXml(xmlCitation);
        assertEquals(citation, converted);
    }

    private JournalArticle create() {
        JournalArticleBuilder builder = new JournalArticleBuilder();
        String title = "Some article title";
        String date = "2008";
        String journalName = "Nature";
        builder.journalName(journalName)
                .firstPage("213")
                .lastPage("223")
                .volume("2")
                .title(title)
                .publicationDate(date)
                .authorsAdd("Sulson J.E.")
                .authorsAdd("JWaterston R.")
                .authoringGroupsSet(Arrays.asList("The C. elegans sequencing consortium"))
                .citationXrefsSet(
                        Arrays.asList(
                                new DBCrossReferenceImpl<>(CitationXrefType.PUBMED, "9851916"),
                                new DBCrossReferenceImpl<>(
                                        CitationXrefType.DOI,
                                        "https://doi.org/10.1126/science.282.5396.2012")));
        JournalArticle citation = builder.build();
        return citation;
    }
}
