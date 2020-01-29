package org.uniprot.core.literature.builder;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createCompleteLiteratureStatistics;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.uniprot.core.ObjectsForTests;
import org.uniprot.core.citation.impl.AuthorImpl;
import org.uniprot.core.citation.impl.JournalImpl;
import org.uniprot.core.literature.LiteratureEntry;

/** @author lgonzales */
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
        assertTrue(entry.isCompleteAuthorList());
    }

    @Test
    void testCompleteLiteratureEntry() {
        LiteratureEntry entry = ObjectsForTests.createCompleteLiteratureEntry();
        validateLiteratureEntry(entry);
    }

    @Test
    void testAddMethodsLiteratureEntry() {
        LiteratureEntry entry = ObjectsForTests.createCompleteLiteratureEntryWithAdd();
        validateLiteratureEntry(entry);
    }

    private void validateLiteratureEntry(LiteratureEntry entry) {
        assertTrue(entry.hasAuthoringGroup());
        MatcherAssert.assertThat(entry.getAuthoringGroup(), Matchers.contains("authoring group"));

        assertTrue(entry.hasAuthors());
        MatcherAssert.assertThat(
                entry.getAuthors(), Matchers.contains(new AuthorImpl("author name")));

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
        assertEquals(entry.getPubmedId(), Long.valueOf(100L));

        assertTrue(entry.hasTitle());
        assertEquals(entry.getTitle(), "title");

        assertTrue(entry.hasVolume());
        assertEquals(entry.getVolume(), "volume");

        assertTrue(entry.hasStatistics());
        assertEquals(entry.getStatistics(), createCompleteLiteratureStatistics());

        assertFalse(entry.isCompleteAuthorList());
    }

    @Test
    void canAddJEntry() {
        LiteratureEntry entry =
                new LiteratureEntryBuilder().journal(new JournalImpl("name")).build();
        assertTrue(entry.hasJournal());
    }

    @Test
    void canCreateBuilderFromInstance() {
        LiteratureEntry obj = new LiteratureEntryBuilder().build();
        LiteratureEntryBuilder builder = LiteratureEntryBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        LiteratureEntry obj = new LiteratureEntryBuilder().build();
        LiteratureEntry obj2 = new LiteratureEntryBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
