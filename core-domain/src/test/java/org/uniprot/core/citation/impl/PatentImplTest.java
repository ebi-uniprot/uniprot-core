package org.uniprot.core.citation.impl;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.CitationDatabase;
import org.uniprot.core.citation.Patent;
import org.uniprot.core.impl.CrossReferenceBuilder;

class PatentImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Patent obj = new PatentImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        CrossReference<CitationDatabase> XREF1 =
                new CrossReferenceBuilder<CitationDatabase>()
                        .database(CitationDatabase.PUBMED)
                        .id("id1")
                        .build();
        Patent impl =
                new PatentImpl(
                        asList("J1", "j2"),
                        Collections.singletonList(new AuthorImpl("auth")),
                        Collections.singletonList(XREF1),
                        "ptitle",
                        new PublicationDateImpl("date"),
                        "pname");
        Patent obj = PatentBuilder.from(impl).build();

        assertTrue(impl.hasPatentNumber());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalNullObject() {
        Patent impl = new PatentImpl(null, null, null, null, null, null);
        Patent obj = PatentBuilder.from(impl).build();

        assertFalse(impl.hasPatentNumber());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
