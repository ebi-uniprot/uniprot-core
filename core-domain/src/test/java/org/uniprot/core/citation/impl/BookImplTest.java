package org.uniprot.core.citation.impl;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.Book;
import org.uniprot.core.citation.CitationDatabase;
import org.uniprot.core.impl.CrossReferenceBuilder;

class BookImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Book obj = new BookImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        CrossReference<CitationDatabase> XREF1 =
                new CrossReferenceBuilder<CitationDatabase>()
                        .database(CitationDatabase.PUBMED)
                        .id("id1")
                        .build();
        Book impl =
                new BookImpl(
                        asList("T1", "T2"),
                        Collections.singletonList(new AuthorImpl("auth")),
                        Collections.singletonList(XREF1),
                        "title",
                        new PublicationDateImpl("date"),
                        "bname",
                        Collections.singletonList(new AuthorImpl("edi")),
                        "fPage",
                        "lPage",
                        "vol",
                        "pub",
                        "add");
        Book obj = BookBuilder.from(impl).build();

        assertTrue(impl.hasBookName());
        assertTrue(impl.hasEditors());
        assertTrue(impl.hasFirstPage());
        assertTrue(impl.hasLastPage());
        assertTrue(impl.hasVolume());
        assertTrue(impl.hasPublisher());
        assertTrue(impl.hasAddress());
        assertTrue(impl.hasTitle());
        assertTrue(impl.hasAuthoringGroup());
        assertTrue(impl.hasAuthors());
        assertTrue(impl.hasPublicationDate());
        assertTrue(impl.getCitationCrossReferenceByType(CitationDatabase.PUBMED).isPresent());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void nullEqual() {
        Book impl =
                new BookImpl(
                        null, null, null, null, null, null, null, null, null, null, null, null);
        Book obj = BookBuilder.from(impl).build();

        assertFalse(impl.hasBookName());
        assertFalse(impl.hasEditors());
        assertFalse(impl.hasFirstPage());
        assertFalse(impl.hasLastPage());
        assertFalse(impl.hasVolume());
        assertFalse(impl.hasPublisher());
        assertFalse(impl.hasAddress());
        assertFalse(impl.hasTitle());
        assertFalse(impl.hasAuthoringGroup());
        assertFalse(impl.hasAuthors());
        assertFalse(impl.hasPublicationDate());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
