package org.uniprot.core.citation.impl;

import static org.junit.jupiter.api.Assertions.*;

import static java.util.Arrays.asList;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.CitationDatabase;
import org.uniprot.core.citation.JournalArticle;
import org.uniprot.core.impl.CrossReferenceBuilder;

import java.util.Collections;

class JournalArticleImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        JournalArticle obj = new JournalArticleImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        CrossReference<CitationDatabase> XREF1 =
                new CrossReferenceBuilder<CitationDatabase>()
                        .database(CitationDatabase.PUBMED)
                        .id("id1")
                        .build();

        JournalArticle impl =
                new JournalArticleImpl(
                        asList("J1", "j2"),
                        Collections.singletonList(new AuthorImpl("auth")),
                        Collections.singletonList(XREF1),
                        "ptitle",
                        new PublicationDateImpl("date"),
                        "jname",
                        "fpage",
                        "lpage",
                        "vol");
        JournalArticle obj = JournalArticleBuilder.from(impl).build();

        assertTrue(impl.hasFirstPage());
        assertTrue(impl.hasJournal());
        assertTrue(impl.hasLastPage());
        assertTrue(impl.hasVolume());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
