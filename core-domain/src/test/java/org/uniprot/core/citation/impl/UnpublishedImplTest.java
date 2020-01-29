package org.uniprot.core.citation.impl;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.citation.CitationXrefType;
import org.uniprot.core.citation.Unpublished;
import org.uniprot.core.citation.builder.UnpublishedBuilder;

class UnpublishedImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Unpublished obj = new UnpublishedImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        DBCrossReference<CitationXrefType> XREF1 =
                new DBCrossReferenceBuilder<CitationXrefType>()
                        .databaseType(CitationXrefType.PUBMED)
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
