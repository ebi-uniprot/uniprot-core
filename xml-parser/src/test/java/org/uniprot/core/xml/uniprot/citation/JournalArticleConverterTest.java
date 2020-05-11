package org.uniprot.core.xml.uniprot.citation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationDatabase;
import org.uniprot.core.citation.JournalArticle;
import org.uniprot.core.citation.impl.JournalArticleBuilder;
import org.uniprot.core.impl.CrossReferenceBuilder;
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
                .citationCrossReferencesAdd(
                        new CrossReferenceBuilder<CitationDatabase>()
                                .database(CitationDatabase.PUBMED)
                                .id("9851916")
                                .build())
                .citationCrossReferencesAdd(
                        new CrossReferenceBuilder<CitationDatabase>()
                                .database(CitationDatabase.DOI)
                                .id("https://doi.org/10.1126/science.282.5396.2012")
                                .build());
        JournalArticle citation = builder.build();
        return citation;
    }
}
