package org.uniprot.core.uniparc.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Property;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jluo
 * @date: 23 May 2019
 */
class UniParcCrossReferenceBuilderTest {

    @Test
    void testVersionI() {
        UniParcCrossReference xref = new UniParcCrossReferenceBuilder().versionI(2).build();
        assertEquals(2, xref.getVersionI());
    }

    @Test
    void testVersion() {
        UniParcCrossReference xref =
                new UniParcCrossReferenceBuilder().versionI(2).version(5).build();
        assertEquals(2, xref.getVersionI());
        assertEquals(5, xref.getVersion().intValue());
    }

    @Test
    void testActive() {
        UniParcCrossReference xref =
                new UniParcCrossReferenceBuilder().versionI(3).version(7).active(true).build();
        assertEquals(3, xref.getVersionI());
        assertEquals(7, xref.getVersion().intValue());
        assertTrue(xref.isActive());
    }

    @Test
    void testCreated() {
        LocalDate created = LocalDate.of(2017, 5, 17);
        UniParcCrossReference xref =
                new UniParcCrossReferenceBuilder()
                        .versionI(3)
                        .version(7)
                        .active(false)
                        .created(created)
                        .build();
        assertEquals(3, xref.getVersionI());
        assertEquals(7, xref.getVersion().intValue());
        assertFalse(xref.isActive());
        assertEquals(created, xref.getCreated());
    }

    @Test
    void testLastUpdated() {
        LocalDate created = LocalDate.of(2017, 5, 17);
        LocalDate lastUpdated = LocalDate.of(2017, 2, 27);
        UniParcCrossReference xref =
                new UniParcCrossReferenceBuilder()
                        .versionI(3)
                        .database(UniParcDatabase.SWISSPROT)
                        .id("P12345")
                        .version(7)
                        .active(true)
                        .created(created)
                        .lastUpdated(lastUpdated)
                        .build();
        assertEquals(3, xref.getVersionI());
        assertEquals(7, xref.getVersion().intValue());
        assertTrue(xref.isActive());
        assertEquals(created, xref.getCreated());
        assertEquals(lastUpdated, xref.getLastUpdated());
        assertEquals(UniParcDatabase.SWISSPROT, xref.getDatabase());
        assertEquals("P12345", xref.getId());
    }

    @Test
    void testProperties() {
        LocalDate created = LocalDate.of(2017, 5, 17);
        LocalDate lastUpdated = LocalDate.of(2017, 2, 27);
        List<Property> properties = new ArrayList<>();
        properties.add(new Property(UniParcCrossReference.PROPERTY_PROTEIN_NAME, "some pname"));
        properties.add(new Property(UniParcCrossReference.PROPERTY_GENE_NAME, "some gname"));
        UniParcCrossReference xref =
                new UniParcCrossReferenceBuilder()
                        .versionI(3)
                        .database(UniParcDatabase.SWISSPROT)
                        .id("P12345")
                        .version(7)
                        .active(true)
                        .created(created)
                        .lastUpdated(lastUpdated)
                        .propertiesSet(properties)
                        .build();
        assertEquals(3, xref.getVersionI());
        assertEquals(7, xref.getVersion().intValue());
        assertTrue(xref.isActive());
        assertEquals(created, xref.getCreated());
        assertEquals(lastUpdated, xref.getLastUpdated());
        assertEquals(UniParcDatabase.SWISSPROT, xref.getDatabase());
        assertEquals("P12345", xref.getId());
        assertEquals(properties, xref.getProperties());
    }

    @Test
    void testFromUniParcDBCrossReference() {
        LocalDate created = LocalDate.of(2017, 5, 17);
        LocalDate lastUpdated = LocalDate.of(2017, 2, 27);
        List<Property> properties = new ArrayList<>();
        properties.add(new Property(UniParcCrossReference.PROPERTY_PROTEIN_NAME, "some pname"));
        properties.add(new Property(UniParcCrossReference.PROPERTY_GENE_NAME, "some gname"));
        UniParcCrossReference xref =
                new UniParcCrossReferenceBuilder()
                        .versionI(3)
                        .database(UniParcDatabase.SWISSPROT)
                        .id("P12345")
                        .version(7)
                        .active(true)
                        .created(created)
                        .lastUpdated(lastUpdated)
                        .propertiesSet(properties)
                        .build();
        assertEquals(3, xref.getVersionI());
        assertEquals(7, xref.getVersion().intValue());
        assertTrue(xref.isActive());
        assertEquals(created, xref.getCreated());
        assertEquals(lastUpdated, xref.getLastUpdated());
        assertEquals(UniParcDatabase.SWISSPROT, xref.getDatabase());
        assertEquals("P12345", xref.getId());
        assertEquals(properties, xref.getProperties());

        UniParcCrossReference newXref = UniParcCrossReferenceBuilder.from(xref).build();
        assertEquals(xref, newXref);
        UniParcCrossReference newXref2 =
                UniParcCrossReferenceBuilder.from(xref).id("P23456").build();
        assertNotEquals(newXref2, xref);
        assertEquals("P23456", newXref2.getId());
    }
}
