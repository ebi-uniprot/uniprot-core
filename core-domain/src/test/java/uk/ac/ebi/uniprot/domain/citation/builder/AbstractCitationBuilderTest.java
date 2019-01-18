package uk.ac.ebi.uniprot.domain.citation.builder;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.citation.*;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;

import java.util.Arrays;

import static org.junit.Assert.*;

public class AbstractCitationBuilderTest {

    @Test
    public void testCreateAuthor() {
        Author author = AbstractCitationBuilder.createAuthor("Tom");
        assertEquals("Tom", author.getValue());
        TestHelper.verifyJson(author);
    }

    @Test
    public void testCreatePublicaitonDate() {
        PublicationDate pubDate = AbstractCitationBuilder.createPublicationDate("2013-AUG");
        assertEquals("2013-AUG", pubDate.getValue());
        TestHelper.verifyJson(pubDate);
    }

    void buildCitationParameters(AbstractCitationBuilder<? extends AbstractCitationBuilder<?,?>,? extends Citation> builder) {
        String title = "Some title";
        builder.title(title).publicationDate(UnpublishedBuilder.createPublicationDate("2015-MAY"))
                .authoringGroups(Arrays.asList("T1", "T2"))
                .authors(Arrays.asList(
                        AbstractCitationBuilder.createAuthor("Tom"),
                        AbstractCitationBuilder.createAuthor("John")))
                .citationXrefs(Arrays.asList(
                        new DBCrossReferenceImpl<>(CitationXrefType.PUBMED, "somepID1"),
                        new DBCrossReferenceImpl<>(CitationXrefType.AGRICOLA, "someID1"),
                        new DBCrossReferenceImpl<>(CitationXrefType.DOI, "someDoiID2")
                ));
    }

    void verifyCitation(Citation citation, CitationType citationType) {
        String title = "Some title";
        assertEquals(title, citation.getTitle());
        assertEquals("2015-MAY", citation.getPublicationDate().getValue());
        assertEquals(2, citation.getAuthoringGroup().size());
        assertEquals(2, citation.getAuthors().size());
        assertEquals("John", citation.getAuthors().get(1).getValue());
        assertEquals("T1", citation.getAuthoringGroup().get(0));
        assertEquals(citationType, citation.getCitationType());

        assertTrue(citation.hasCitationXrefs());
        assertNotNull(citation.getCitationXrefs());
        assertEquals(3, citation.getCitationXrefs().size());
        assertTrue(citation.getCitationXrefsByType(CitationXrefType.PUBMED).isPresent());
        assertTrue(citation.getCitationXrefsByType(CitationXrefType.AGRICOLA).isPresent());
        assertTrue(citation.getCitationXrefsByType(CitationXrefType.DOI).isPresent());
    }
}
