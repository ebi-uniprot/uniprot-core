package org.uniprot.core.citation.impl;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.builder.CrossReferenceBuilder;
import org.uniprot.core.citation.CitationDatabase;
import org.uniprot.core.citation.Thesis;
import org.uniprot.core.citation.builder.ThesisBuilder;

class ThesisImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Thesis obj = new ThesisImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        CrossReference<CitationDatabase> XREF1 =
                new CrossReferenceBuilder<CitationDatabase>()
                        .databaseType(CitationDatabase.PUBMED)
                        .id("id1")
                        .build();

        Thesis impl =
                new ThesisImpl(
                        asList("T1", "T2"),
                        Collections.singletonList(new AuthorImpl("auth")),
                        Collections.singletonList(XREF1),
                        "title",
                        new PublicationDateImpl("date"),
                        "institute",
                        "address");
        Thesis obj = ThesisBuilder.from(impl).build();

        assertTrue(impl.hasAddress());
        assertTrue(impl.hasInstitute());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
