package org.uniprot.core.citation.impl;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.citation.CitationXrefType;
import org.uniprot.core.citation.JournalArticle;
import org.uniprot.core.citation.builder.JournalArticleBuilder;

class JournalArticleImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        JournalArticle obj = new JournalArticleImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        DBCrossReference<CitationXrefType> XREF1 =
                new DBCrossReferenceBuilder<CitationXrefType>()
                        .databaseType(CitationXrefType.PUBMED)
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
