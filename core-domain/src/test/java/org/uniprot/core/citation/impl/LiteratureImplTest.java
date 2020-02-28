package org.uniprot.core.citation.impl;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.citation.CitationDatabase;
import org.uniprot.core.citation.Literature;
import org.uniprot.core.citation.builder.LiteratureBuilder;

/**
 * @author lgonzales
 * @since 2020-01-28
 */
class LiteratureImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Literature obj = new LiteratureImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        DBCrossReference<CitationDatabase> XREF1 =
                new DBCrossReferenceBuilder<CitationDatabase>()
                        .databaseType(CitationDatabase.PUBMED)
                        .id("12345")
                        .build();

        DBCrossReference<CitationDatabase> XREF2 =
                new DBCrossReferenceBuilder<CitationDatabase>()
                        .databaseType(CitationDatabase.DOI)
                        .id("DoidId")
                        .build();

        Literature impl =
                new LiteratureImpl(
                        asList("J1", "j2"),
                        Collections.singletonList(new AuthorImpl("auth")),
                        asList(XREF1, XREF2),
                        "ptitle",
                        new PublicationDateImpl("date"),
                        "jname",
                        "fpage",
                        "lpage",
                        "vol",
                        "abstract",
                        true);
        Literature obj = LiteratureBuilder.from(impl).build();

        assertTrue(impl.hasFirstPage());
        assertTrue(impl.hasJournal());
        assertTrue(impl.hasLastPage());
        assertTrue(impl.hasVolume());
        assertTrue(impl.isCompleteAuthorList());
        assertTrue(impl.hasLiteratureAbstract());
        assertTrue(impl.hasPubmedId());
        assertTrue(impl.hasDoiId());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
