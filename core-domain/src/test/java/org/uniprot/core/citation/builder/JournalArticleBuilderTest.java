package org.uniprot.core.citation.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.CitationType;
import org.uniprot.core.citation.JournalArticle;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        builder.journalName(journalName)
                .firstPage("213");
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
        builder.journalName(journalName)
                .firstPage("213")
                .lastPage("223");
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
        builder.journalName(journalName)
                .firstPage("213")
                .lastPage("223")
                .volume("2");
        JournalArticle citation = builder.build();
        this.verifyCitation(citation, CitationType.JOURNAL_ARTICLE);
        assertEquals(journalName, citation.getJournal().getName());
        assertEquals("213", citation.getFirstPage());
        assertEquals("223", citation.getLastPage());
        assertEquals("2", citation.getVolume());
    }
}
