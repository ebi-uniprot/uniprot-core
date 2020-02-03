package org.uniprot.core.citation.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.citation.CitationType;
import org.uniprot.core.citation.CitationXrefType;
import org.uniprot.core.citation.JournalArticle;

class JournalArticleBuilderTest extends AbstractCitationBuilderTest {
    @Test
    void testBuildCitation() {
        JournalArticleBuilder builder = new JournalArticleBuilder();
        this.buildCitationParameters(builder);
        JournalArticle citation = builder.build();
        this.verifyCitation(citation, CitationType.JOURNAL_ARTICLE);
    }

    @Test
    void testAddJournalName() {
        JournalArticleBuilder builder = new JournalArticleBuilder();
        this.buildCitationParameters(builder);
        String journalName = "Nature";
        builder.journalName(journalName);
        JournalArticle citation = builder.build();
        this.verifyCitation(citation, CitationType.JOURNAL_ARTICLE);
        assertEquals(journalName, citation.getJournal().getName());
    }

    @Test
    void testAddFirstPage() {
        JournalArticleBuilder builder = new JournalArticleBuilder();
        this.buildCitationParameters(builder);
        String journalName = "Nature";
        builder.journalName(journalName).firstPage("213");
        JournalArticle citation = builder.build();
        this.verifyCitation(citation, CitationType.JOURNAL_ARTICLE);
        assertEquals(journalName, citation.getJournal().getName());
        assertEquals("213", citation.getFirstPage());
    }

    @Test
    void testAddLastPage() {
        JournalArticleBuilder builder = new JournalArticleBuilder();
        this.buildCitationParameters(builder);
        String journalName = "Nature";
        builder.journalName(journalName).firstPage("213").lastPage("223");
        JournalArticle citation = builder.build();
        this.verifyCitation(citation, CitationType.JOURNAL_ARTICLE);
        assertEquals(journalName, citation.getJournal().getName());
        assertEquals("213", citation.getFirstPage());
        assertEquals("223", citation.getLastPage());
    }

    @Test
    void testAddVolume() {
        JournalArticleBuilder builder = new JournalArticleBuilder();
        this.buildCitationParameters(builder);
        String journalName = "Nature";
        builder.journalName(journalName).firstPage("213").lastPage("223").volume("2");
        JournalArticle citation = builder.build();
        this.verifyCitation(citation, CitationType.JOURNAL_ARTICLE);
        assertEquals(journalName, citation.getJournal().getName());
        assertEquals("213", citation.getFirstPage());
        assertEquals("223", citation.getLastPage());
        assertEquals("2", citation.getVolume());
    }

    @Test
    void canAddSingleCitation() {
        DBCrossReference<CitationXrefType> citation =
                new DBCrossReferenceBuilder<CitationXrefType>()
                        .databaseType(CitationXrefType.PUBMED)
                        .id("pubmedId")
                        .build();
        JournalArticle article = new JournalArticleBuilder().addCitationXrefs(citation).build();
        assertEquals(citation, article.getCitationXrefs().get(0));
    }
}
