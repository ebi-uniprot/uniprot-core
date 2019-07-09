package uk.ac.ebi.uniprot.domain.literature.builder;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.citation.impl.AuthorImpl;
import uk.ac.ebi.uniprot.domain.citation.impl.PublicationDateImpl;
import uk.ac.ebi.uniprot.domain.literature.LiteratureEntry;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lgonzales
 */
class LiteratureEntryBuilderTest {

    @Test
    void testSimpleLiteratureEntry() {
        LiteratureEntry entry = new LiteratureEntryBuilder().build();

        assertFalse(entry.hasAuthoringGroup());
        assertTrue(entry.getAuthoringGroup().isEmpty());

        assertFalse(entry.hasAuthors());
        assertTrue(entry.getAuthors().isEmpty());

        assertFalse(entry.hasDoiId());
        assertFalse(entry.hasFirstPage());
        assertFalse(entry.hasJournal());
        assertFalse(entry.hasLastPage());
        assertFalse(entry.hasLiteratureAbstract());
        assertFalse(entry.hasPublicationDate());
        assertFalse(entry.hasPubmedId());
        assertFalse(entry.hasTitle());
        assertFalse(entry.hasVolume());
        assertFalse(entry.hasStatistics());
        assertFalse(entry.hasLiteratureMappedReferences());
        assertTrue(entry.isCompleteAuthorList());

    }

    @Test
    void testCompleteLiteratureEntry() {
        LiteratureEntry entry = createCompleteLiteratureEntry();
        validateLiteratureEntry(entry);
    }

    @Test
    void testAddMethodsLiteratureEntry() {
        LiteratureEntry entry = createCompleteLiteratureEntryWithAdd();
        validateLiteratureEntry(entry);
    }

    private void validateLiteratureEntry(LiteratureEntry entry) {
        assertTrue(entry.hasAuthoringGroup());
        MatcherAssert.assertThat(entry.getAuthoringGroup(), Matchers.contains("authoring group"));

        assertTrue(entry.hasAuthors());
        MatcherAssert.assertThat(entry.getAuthors(), Matchers.contains(new AuthorImpl("author name")));

        assertTrue(entry.hasDoiId());
        assertEquals(entry.getDoiId(), "doi Id");

        assertTrue(entry.hasFirstPage());
        assertEquals(entry.getFirstPage(), "first Page");

        assertTrue(entry.hasJournal());
        assertEquals(entry.getJournal().getName(), "journal Name");

        assertTrue(entry.hasLastPage());
        assertEquals(entry.getLastPage(), "last Page");

        assertTrue(entry.hasLiteratureAbstract());
        assertEquals(entry.getLiteratureAbstract(), "literature Abstract");

        assertTrue(entry.hasPublicationDate());
        assertEquals(entry.getPublicationDate().getValue(), "21-06-2019");

        assertTrue(entry.hasPubmedId());
        assertEquals(entry.getPubmedId(), 100L);

        assertTrue(entry.hasTitle());
        assertEquals(entry.getTitle(), "title");

        assertTrue(entry.hasVolume());
        assertEquals(entry.getVolume(), "volume");

        assertTrue(entry.hasStatistics());
        assertEquals(entry.getStatistics(), LiteratureStatisticsBuilderTest.createCompleteLiteratureStatistics());

        assertTrue(entry.hasLiteratureMappedReferences());
        MatcherAssert.assertThat(entry.getLiteratureMappedReferences(), Matchers.contains(LiteratureMappedReferenceBuilderTest.createCompleteLiteratureMappedReference()));

        assertFalse(entry.isCompleteAuthorList());

    }

    private LiteratureEntry createCompleteLiteratureEntry() {
        return createBasicLiteratureEntryBuilder()
                .authors(Collections.singletonList(new AuthorImpl("author name")))
                .authoringGroup(Collections.singletonList("authoring group"))
                .literatureMappedReference(Collections.singletonList(LiteratureMappedReferenceBuilderTest.createCompleteLiteratureMappedReference()))
                .build();
    }

    private LiteratureEntry createCompleteLiteratureEntryWithAdd() {
        return createBasicLiteratureEntryBuilder()
                .addAuthor(new AuthorImpl("author name"))
                .addAuthoringGroup("authoring group")
                .addLiteratureMappedReference(LiteratureMappedReferenceBuilderTest.createCompleteLiteratureMappedReference())
                .build();
    }

    private LiteratureEntryBuilder createBasicLiteratureEntryBuilder() {
        return new LiteratureEntryBuilder()
                .doiId("doi Id")
                .pubmedId(100L)
                .firstPage("first Page")
                .journal("journal Name")
                .volume("volume")
                .lastPage("last Page")
                .literatureAbstract("literature Abstract")
                .publicationDate(new PublicationDateImpl("21-06-2019"))
                .statistics(LiteratureStatisticsBuilderTest.createCompleteLiteratureStatistics())
                .title("title")
                .completeAuthorList(false);
    }

}