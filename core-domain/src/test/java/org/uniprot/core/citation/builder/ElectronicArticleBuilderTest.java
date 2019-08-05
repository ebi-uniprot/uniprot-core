package org.uniprot.core.citation.builder;

import org.junit.Test;
import org.uniprot.core.TestHelper;
import org.uniprot.core.citation.CitationType;
import org.uniprot.core.citation.ElectronicArticle;
import org.uniprot.core.citation.builder.ElectronicArticleBuilder;

import static org.junit.Assert.assertEquals;

public class ElectronicArticleBuilderTest extends AbstractCitationBuilderTest {
    @Test
    public void testBuildCitation() {
        ElectronicArticleBuilder builder = new ElectronicArticleBuilder();
        this.buildCitationParameters(builder);
        ElectronicArticle citation = builder.build();
        this.verifyCitation(citation, CitationType.ELECTRONIC_ARTICLE);
        TestHelper.verifyJson(citation);
    }

    @Test
    public void testAddJournalName() {
        ElectronicArticleBuilder builder = new ElectronicArticleBuilder();
        this.buildCitationParameters(builder);
        String journalName = "Nature";
        builder.journalName(journalName);
        ElectronicArticle citation = builder.build();
        this.verifyCitation(citation, CitationType.ELECTRONIC_ARTICLE);
        assertEquals(journalName, citation.getJournal().getName());
        TestHelper.verifyJson(citation);
    }

    @Test
    public void testAddLocator() {
        ElectronicArticleBuilder builder = new ElectronicArticleBuilder();
        this.buildCitationParameters(builder);
        String journalName = "Nature";
        builder.journalName(journalName)
                .locator("Some locator");
        ElectronicArticle citation = builder.build();
        this.verifyCitation(citation, CitationType.ELECTRONIC_ARTICLE);
        assertEquals(journalName, citation.getJournal().getName());
        assertEquals("Some locator", citation.getLocator().getValue());
        TestHelper.verifyJson(citation);
    }
}
