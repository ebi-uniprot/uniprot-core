package org.uniprot.core.citation.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.Property;
import org.uniprot.core.citation.CitationDatabase;
import org.uniprot.core.impl.CrossReferenceBuilder;

/**
 * Created 22/01/19
 *
 * @author Edd
 */
class CrossReferenceBuilderTest {
    @Test
    void canCreateInstance() {
        CrossReference<CitationDatabase> xref =
                new CrossReferenceBuilder<CitationDatabase>()
                        .database(CitationDatabase.PUBMED)
                        .id("id1")
                        .build();

        CrossReference<CitationDatabase> copiedInstance = CrossReferenceBuilder.from(xref).build();

        assertThat(xref, is(copiedInstance));
    }

    @Test
    void addSinglePropertyUsingString_keyNull_willBeIgnore() {
        CrossReference<CitationDatabase> cr =
                new CrossReferenceBuilder<CitationDatabase>().propertiesAdd(null, "value").build();
        assertTrue(cr.getProperties().isEmpty());
    }

    @Test
    void addSinglePropertyUsingString_valueNull_willBeIgnore() {
        CrossReference<CitationDatabase> cr =
                new CrossReferenceBuilder<CitationDatabase>().propertiesAdd("key", null).build();
        assertTrue(cr.getProperties().isEmpty());
    }

    @Test
    void addSinglePropertyUsingString() {
        CrossReference<CitationDatabase> cr =
                new CrossReferenceBuilder<CitationDatabase>().propertiesAdd("key", "value").build();
        assertFalse(cr.getProperties().isEmpty());
        assertEquals(1, cr.getProperties().size());
    }

    @Test
    void addSinglePropertyUsingObject_nullWillIgnore() {
        CrossReference<CitationDatabase> cr =
                new CrossReferenceBuilder<CitationDatabase>().propertiesAdd(null).build();
        assertTrue(cr.getProperties().isEmpty());
    }

    @Test
    void addSinglePropertyUsingObject() {
        Property property = new Property(null, null);
        CrossReference<CitationDatabase> cr =
                new CrossReferenceBuilder<CitationDatabase>().propertiesAdd(property).build();
        assertFalse(cr.getProperties().isEmpty());
        assertEquals(1, cr.getProperties().size());
    }
}
