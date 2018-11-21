package uk.ac.ebi.uniprot.domain.uniprot.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.JournalArticle;
import uk.ac.ebi.uniprot.domain.citation.builder.BookBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.ElectronicArticleBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.JournalArticleBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.PatentBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.SubmissionBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.ThesisBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.UnpublishedBuilder;

public class CitationFactoryTest {

    @Test
    public void testCreateCitation() {
        JournalArticleBuilder builder = JournalArticleBuilder.newInstance();
        String title ="some title";
        builder.title(title)
        .publicationDate(UnpublishedBuilder.createPublicationDate("2015-MAY"))
        .authoringGroups(Arrays.asList(new String[]{"T1", "T2" }))
        .authors(Arrays.asList(new Author[] {JournalArticleBuilder.createAuthor("Tom"),
                JournalArticleBuilder.createAuthor("John")               
        }
        ));
        String journalName ="Nature";
        builder.journalName(journalName)
        .firstPage("213")
        .lastPage("223")
        .volume("2");
        
        JournalArticle ja = builder.build();
        
        JournalArticle ja2 =CitationFactory.INSTANCE.createCitation(builder);
        assertEquals(ja, ja2);
    }

    @Test
    public void testCreateBookBuilder() {
       BookBuilder builder =CitationFactory.INSTANCE.createBookBuilder();
       assertNotNull(builder);
    }

    @Test
    public void testCreateElectronicArticleBuilder() {
        ElectronicArticleBuilder builder = CitationFactory.INSTANCE.createElectronicArticleBuilder();
        assertNotNull(builder);
    }

    @Test
    public void testCreateJournalArticleBuilder() {
        JournalArticleBuilder builder = CitationFactory.INSTANCE.createJournalArticleBuilder();
        assertNotNull(builder);
    }

    @Test
    public void testCreatePatentBuilder() {
        PatentBuilder builder = CitationFactory.INSTANCE.createPatentBuilder();
        assertNotNull(builder);
    }

    @Test
    public void testCreateSubmissionBuilder() {
        SubmissionBuilder builder = CitationFactory.INSTANCE.createSubmissionBuilder();
        assertNotNull(builder);
    }

    @Test
    public void testCreateThesisBuilder() {
        ThesisBuilder builder = CitationFactory.INSTANCE.createThesisBuilder();
        assertNotNull(builder);
    }

    @Test
    public void testCreateUnpublishedObservationsBuilder() {
        UnpublishedBuilder builder = CitationFactory.INSTANCE.createUnpublishedObservationsBuilder();
        assertNotNull(builder);
    }

  
}
