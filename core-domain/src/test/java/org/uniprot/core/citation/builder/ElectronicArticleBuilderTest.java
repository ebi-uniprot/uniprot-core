package org.uniprot.core.citation.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.CitationType;
import org.uniprot.core.citation.ElectronicArticle;

class ElectronicArticleBuilderTest extends AbstractCitationBuilderTest {
    @Test
    void testBuildCitation() {
        ElectronicArticleBuilder builder = new ElectronicArticleBuilder();
        this.buildCitationParameters(builder);
        ElectronicArticle citation = builder.build();
        this.verifyCitation(citation, CitationType.ELECTRONIC_ARTICLE);
    }

    @Test
    void testAddJournalName() {
        ElectronicArticleBuilder builder = new ElectronicArticleBuilder();
        this.buildCitationParameters(builder);
        String journalName = "Nature";
        builder.journalName(journalName);
        ElectronicArticle citation = builder.build();
        this.verifyCitation(citation, CitationType.ELECTRONIC_ARTICLE);
        assertEquals(journalName, citation.getJournal().getName());
    }

    @Test
    void testAddLocator() {
        ElectronicArticleBuilder builder = new ElectronicArticleBuilder();
        this.buildCitationParameters(builder);
        String journalName = "Nature";
        builder.journalName(journalName).locator("Some locator");
        ElectronicArticle citation = builder.build();
        this.verifyCitation(citation, CitationType.ELECTRONIC_ARTICLE);
        assertEquals(journalName, citation.getJournal().getName());
        assertEquals("Some locator", citation.getLocator().getValue());
    }
}
