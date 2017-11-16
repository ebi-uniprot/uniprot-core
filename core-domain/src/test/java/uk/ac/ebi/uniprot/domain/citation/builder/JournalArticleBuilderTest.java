package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.JournalArticle;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JournalArticleBuilderTest extends AbstractCitationBuilderTest {

    @Test
    public void testBuildCitation() {
        JournalArticleBuilder builder = JournalArticleBuilder.newInstance();
        this.builderCitationParamters(builder);
        JournalArticle citation = builder.build();
        this.verifyCitation(citation, CitationType.JOURNAL_ARTICLE);
    }

    @Test
    public void testAddJournalName() {
        JournalArticleBuilder builder = JournalArticleBuilder.newInstance();
        this.builderCitationParamters(builder);
        String journalName ="Nature";
        builder.journalName(journalName);
        JournalArticle citation = builder.build();
        this.verifyCitation(citation, CitationType.JOURNAL_ARTICLE);
        assertEquals(journalName, citation.getJournalName());    
    }


    @Test
    public void testAddFirstPage() {
        JournalArticleBuilder builder = JournalArticleBuilder.newInstance();
        this.builderCitationParamters(builder);
        String journalName ="Nature";
        builder.journalName(journalName)
        .firstPage("213");
        JournalArticle citation = builder.build();
        this.verifyCitation(citation, CitationType.JOURNAL_ARTICLE);
        assertEquals(journalName, citation.getJournalName());    
        assertEquals("213", citation.getFirstPage());
    }

    @Test
    public void testAddLastPage() {
        JournalArticleBuilder builder = JournalArticleBuilder.newInstance();
        this.builderCitationParamters(builder);
        String journalName ="Nature";
        builder.journalName(journalName)
        .firstPage("213")
        .lastPage("223");
        JournalArticle citation = builder.build();
        this.verifyCitation(citation, CitationType.JOURNAL_ARTICLE);
        assertEquals(journalName, citation.getJournalName());    
        assertEquals("213", citation.getFirstPage());
        assertEquals("223", citation.getLastPage());
    }

    @Test
    public void testAddVolume() {
        JournalArticleBuilder builder = JournalArticleBuilder.newInstance();
        this.builderCitationParamters(builder);
        String journalName ="Nature";
        builder.journalName(journalName)
        .firstPage("213")
        .lastPage("223")
        .volume("2");
        JournalArticle citation = builder.build();
        this.verifyCitation(citation, CitationType.JOURNAL_ARTICLE);
        assertEquals(journalName, citation.getJournalName());    
        assertEquals("213", citation.getFirstPage());
        assertEquals("223", citation.getLastPage());
        assertEquals("2", citation.getVolume());
    }

}
