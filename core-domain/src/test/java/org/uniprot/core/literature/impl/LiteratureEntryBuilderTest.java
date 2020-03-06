package org.uniprot.core.literature.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.*;
import static org.uniprot.core.ObjectsForTests.createCompleteLiteratureCitation;
import static org.uniprot.core.ObjectsForTests.createCompleteLiteratureStatistics;

import org.junit.jupiter.api.Test;
import org.uniprot.core.literature.LiteratureEntry;

/** @author lgonzales */
class LiteratureEntryBuilderTest {

    @Test
    void testSimpleLiteratureEntry() {
        LiteratureEntry entry = new LiteratureEntryBuilder().build();
        assertFalse(entry.hasCitation());
        assertFalse(entry.hasStatistics());
    }

    @Test
    void testCompleteLiteratureEntry() {
        LiteratureEntry entry = createCompleteLiteratureEntry();
        validateLiteratureEntry(entry);
    }

    private void validateLiteratureEntry(LiteratureEntry entry) {
        assertTrue(entry.hasStatistics());
        assertEquals(entry.getStatistics(), createCompleteLiteratureStatistics());

        assertTrue(entry.hasCitation());
        assertEquals(entry.getCitation(), createCompleteLiteratureCitation());
    }

    @Test
    void canCreateBuilderFromInstance() {
        LiteratureEntry obj = new LiteratureEntryBuilder().build();
        LiteratureEntryBuilder builder = LiteratureEntryBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        LiteratureEntry obj = createCompleteLiteratureEntry();
        LiteratureEntry obj2 = createCompleteLiteratureEntry();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
