package org.uniprot.core.citation.builder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.Property;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.citation.CitationXrefType;

/**
 * Created 22/01/19
 *
 * @author Edd
 */
class DBCrossReferenceBuilderTest {
    @Test
    void canCreateInstance() {
        DBCrossReference<CitationXrefType> xref =
                new DBCrossReferenceBuilder<CitationXrefType>()
                        .databaseType(CitationXrefType.PUBMED)
                        .id("id1")
                        .build();

        DBCrossReference<CitationXrefType> copiedInstance =
                DBCrossReferenceBuilder.from(xref).build();

        assertThat(xref, is(copiedInstance));
    }

    @Test
    void addSinglePropertyUsingString_keyNull_willBeIgnore() {
        DBCrossReference<CitationXrefType> cr =
                new DBCrossReferenceBuilder<CitationXrefType>()
                        .propertiesAdd(null, "value")
                        .build();
        assertTrue(cr.getProperties().isEmpty());
    }

    @Test
    void addSinglePropertyUsingString_valueNull_willBeIgnore() {
        DBCrossReference<CitationXrefType> cr =
                new DBCrossReferenceBuilder<CitationXrefType>().propertiesAdd("key", null).build();
        assertTrue(cr.getProperties().isEmpty());
    }

    @Test
    void addSinglePropertyUsingString() {
        DBCrossReference<CitationXrefType> cr =
                new DBCrossReferenceBuilder<CitationXrefType>()
                        .propertiesAdd("key", "value")
                        .build();
        assertFalse(cr.getProperties().isEmpty());
        assertEquals(1, cr.getProperties().size());
    }

    @Test
    void addSinglePropertyUsingObject_nullWillIgnore() {
        DBCrossReference<CitationXrefType> cr =
                new DBCrossReferenceBuilder<CitationXrefType>().propertiesAdd(null).build();
        assertTrue(cr.getProperties().isEmpty());
    }

    @Test
    void addSinglePropertyUsingObject() {
        Property property = new Property(null, null);
        DBCrossReference<CitationXrefType> cr =
                new DBCrossReferenceBuilder<CitationXrefType>().propertiesAdd(property).build();
        assertFalse(cr.getProperties().isEmpty());
        assertEquals(1, cr.getProperties().size());
    }
}
