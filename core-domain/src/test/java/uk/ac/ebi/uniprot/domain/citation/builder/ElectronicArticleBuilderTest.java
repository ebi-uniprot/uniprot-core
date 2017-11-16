package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.ElectronicArticle;
import uk.ac.ebi.uniprot.domain.citation.Locator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ElectronicArticleBuilderTest  extends  AbstractCitationBuilderTest{

    @Test
    public void testCreateLocator() {
        String locStr ="Some location";
       Locator locator = ElectronicArticleBuilder.createLocator(locStr);
       assertEquals(locStr, locator.getValue());
    }

    @Test
    public void testBuildCitation() {
        ElectronicArticleBuilder builder = ElectronicArticleBuilder.newInstance();
        this.builderCitationParamters(builder);
        ElectronicArticle citation = builder.build();
        this.verifyCitation(citation, CitationType.ELECTRONIC_ARTICLE);
    }

    @Test
    public void testAddJournalName() {
        ElectronicArticleBuilder builder = ElectronicArticleBuilder.newInstance();
        this.builderCitationParamters(builder);
        String journalName ="Nature";
        builder.journalName(journalName);
        ElectronicArticle citation = builder.build();
        this.verifyCitation(citation, CitationType.ELECTRONIC_ARTICLE);
        assertEquals(journalName, citation.getJournalName());    
    }

    @Test
    public void testAddLocator() {
        ElectronicArticleBuilder builder = ElectronicArticleBuilder.newInstance();
        this.builderCitationParamters(builder);
        String journalName ="Nature";
        builder.journalName(journalName)
        .locator("Some locator");
        ElectronicArticle citation = builder.build();
        this.verifyCitation(citation, CitationType.ELECTRONIC_ARTICLE);
        assertEquals(journalName, citation.getJournalName());    
        assertEquals("Some locator", citation.getLocator().getValue());
    }

}
