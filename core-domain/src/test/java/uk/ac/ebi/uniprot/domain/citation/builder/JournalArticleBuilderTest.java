package uk.ac.ebi.uniprot.domain.citation.builder;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.JournalArticle;

import static org.junit.Assert.assertEquals;

public class JournalArticleBuilderTest extends AbstractCitationBuilderTest {
    @Test
    public void testBuildCitation() {
        JournalArticleBuilder builder = new JournalArticleBuilder();
        this.buildCitationParameters(builder);
        JournalArticle citation = builder.build();
        this.verifyCitation(citation, CitationType.JOURNAL_ARTICLE);
        TestHelper.verifyJson(citation);
    }

    @Test
    public void testAddJournalName() {
        JournalArticleBuilder builder = new JournalArticleBuilder();
        this.buildCitationParameters(builder);
        String journalName = "Nature";
        builder.journalName(journalName);
        JournalArticle citation = builder.build();
        this.verifyCitation(citation, CitationType.JOURNAL_ARTICLE);
        assertEquals(journalName, citation.getJournal().getName());
        TestHelper.verifyJson(citation);
    }

    @Test
    public void testAddFirstPage() {
        JournalArticleBuilder builder = new JournalArticleBuilder();
        this.buildCitationParameters(builder);
        String journalName = "Nature";
        builder.journalName(journalName)
                .firstPage("213");
        JournalArticle citation = builder.build();
        this.verifyCitation(citation, CitationType.JOURNAL_ARTICLE);
        assertEquals(journalName, citation.getJournal().getName());
        assertEquals("213", citation.getFirstPage());
        TestHelper.verifyJson(citation);
    }

    @Test
    public void testAddLastPage() {
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
        TestHelper.verifyJson(citation);
    }

    @Test
    public void testAddVolume() {
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
        TestHelper.verifyJson(citation);
    }
}
