package org.uniprot.core.citation.impl;

import static org.junit.jupiter.api.Assertions.*;

import static java.util.Arrays.asList;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.CitationDatabase;
import org.uniprot.core.citation.Unpublished;
import org.uniprot.core.impl.CrossReferenceBuilder;

import java.util.Collections;

class UnpublishedImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Unpublished obj = new UnpublishedImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        CrossReference<CitationDatabase> XREF1 =
                new CrossReferenceBuilder<CitationDatabase>()
                        .database(CitationDatabase.PUBMED)
                        .id("id1")
                        .build();

        Unpublished impl =
                new UnpublishedImpl(
                        asList("T1", "T2"),
                        Collections.singletonList(new AuthorImpl("auth")),
                        Collections.singletonList(XREF1),
                        "title",
                        new PublicationDateImpl("date"));
        Unpublished obj = UnpublishedBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
