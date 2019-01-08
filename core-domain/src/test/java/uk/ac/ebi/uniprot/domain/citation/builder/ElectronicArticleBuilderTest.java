package uk.ac.ebi.uniprot.domain.citation.builder;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.ElectronicArticle;
import uk.ac.ebi.uniprot.domain.citation.Locator;

import static org.junit.Assert.assertEquals;

public class ElectronicArticleBuilderTest extends AbstractCitationBuilderTest {

    @Test
    public void testCreateLocator() {
        String locStr = "Some location";
        Locator locator = ElectronicArticleBuilder.createLocator(locStr);
        assertEquals(locStr, locator.getValue());
        TestHelper.verifyJson(locator);
    }

    @Test
    public void testBuildCitation() {
        ElectronicArticleBuilder builder = ElectronicArticleBuilder.newInstance();
        this.buildCitationParameters(builder);
        ElectronicArticle citation = builder.build();
        this.verifyCitation(citation, CitationType.ELECTRONIC_ARTICLE);
        TestHelper.verifyJson(citation);
    }

    @Test
    public void testAddJournalName() {
        ElectronicArticleBuilder builder = ElectronicArticleBuilder.newInstance();
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
        ElectronicArticleBuilder builder = ElectronicArticleBuilder.newInstance();
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
