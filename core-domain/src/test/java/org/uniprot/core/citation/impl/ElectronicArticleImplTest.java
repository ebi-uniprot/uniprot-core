package org.uniprot.core.citation.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.citation.CitationXrefType;
import org.uniprot.core.citation.ElectronicArticle;
import org.uniprot.core.citation.Locator;
import org.uniprot.core.citation.builder.ElectronicArticleBuilder;

class ElectronicArticleImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization_locator() {
        Locator obj = new ElectronicArticleImpl.LocatorImpl();
        assertFalse(obj.hasValue());
        assertNotNull(obj);
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        ElectronicArticle obj = new ElectronicArticleImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        DBCrossReference<CitationXrefType> XREF1 =
                new DBCrossReferenceBuilder<CitationXrefType>()
                        .databaseType(CitationXrefType.PUBMED)
                        .id("id1")
                        .build();

        ElectronicArticle impl =
                new ElectronicArticleImpl(
                        Arrays.asList("G1", "G2"),
                        Collections.singletonList(new AuthorImpl("auth")),
                        Collections.singletonList(XREF1),
                        "title",
                        new PublicationDateImpl(""),
                        "jn",
                        new ElectronicArticleImpl.LocatorImpl(""));
        ElectronicArticle obj = ElectronicArticleBuilder.from(impl).build();

        assertTrue(impl.hasJournal());
        assertTrue(impl.hasLocator());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
