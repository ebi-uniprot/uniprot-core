package org.uniprot.core.literature.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.*;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.uniprot.core.ObjectsForTests;
import org.uniprot.core.literature.LiteratureStoreEntry;

/**
 * @author lgonzales
 * @since 2019-12-05
 */
class LiteratureStoreEntryBuilderTest {

    @Test
    void testSimpleLiteratureStoreEntry() {
        LiteratureStoreEntry entry = new LiteratureStoreEntryBuilder().build();

        assertFalse(entry.hasLiteratureEntry());
        assertFalse(entry.hasLiteratureMappedReferences());
        assertTrue(entry.getLiteratureMappedReferences().isEmpty());
    }

    @Test
    void testCompleteLiteratureEntry() {
        LiteratureStoreEntry entry = ObjectsForTests.createCompleteLiteratureStoreEntry();
        validateLiteratureEntry(entry);
    }

    @Test
    void testAddMethodsLiteratureEntry() {
        LiteratureStoreEntry entry = ObjectsForTests.createCompleteLiteratureStoreEntryWithAdd();
        validateLiteratureEntry(entry);
    }

    private void validateLiteratureEntry(LiteratureStoreEntry entry) {
        assertTrue(entry.hasLiteratureMappedReferences());
        MatcherAssert.assertThat(
                entry.getLiteratureMappedReferences(),
                Matchers.contains(createCompleteLiteratureMappedReference()));

        assertTrue(entry.hasLiteratureEntry());
        assertEquals(entry.getLiteratureEntry(), createCompleteLiteratureEntry());
    }

    @Test
    void canCreateBuilderFromInstance() {
        LiteratureStoreEntry obj = new LiteratureStoreEntryBuilder().build();
        LiteratureStoreEntryBuilder builder = LiteratureStoreEntryBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        LiteratureStoreEntry obj = new LiteratureStoreEntryBuilder().build();
        LiteratureStoreEntry obj2 = new LiteratureStoreEntryBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
