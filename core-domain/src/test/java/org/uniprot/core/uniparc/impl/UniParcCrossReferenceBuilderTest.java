package org.uniprot.core.uniparc.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Property;
import org.uniprot.core.uniparc.ProteomeIdComponent;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;

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
        UniParcCrossReference xref = new UniParcCrossReferenceBuilder().created(created).build();
        assertEquals(created, xref.getCreated());
    }

    @Test
    void testLastUpdated() {
        LocalDate lastUpdated = LocalDate.of(2017, 2, 27);
        UniParcCrossReference xref =
                new UniParcCrossReferenceBuilder().lastUpdated(lastUpdated).build();
        assertEquals(lastUpdated, xref.getLastUpdated());
    }

    @Test
    void testProperties() {
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("prop1", "some pname"));
        properties.add(new Property("prop2", "some gname"));
        UniParcCrossReference xref =
                new UniParcCrossReferenceBuilder().propertiesSet(properties).build();
        assertEquals(properties, xref.getProperties());
    }

    @Test
    void testGeneName() {
        String geneName = "gene Name value";
        UniParcCrossReference xref = new UniParcCrossReferenceBuilder().geneName(geneName).build();
        assertEquals(geneName, xref.getGeneName());
    }

    @Test
    void testProteinName() {
        String proteinName = "protein Name value";
        UniParcCrossReference xref =
                new UniParcCrossReferenceBuilder().proteinName(proteinName).build();
        assertEquals(proteinName, xref.getProteinName());
    }

    @Test
    void testOrganism() {
        Organism organism = new OrganismBuilder().taxonId(123L).scientificName("sName").build();
        UniParcCrossReference xref = new UniParcCrossReferenceBuilder().organism(organism).build();
        assertEquals(organism, xref.getOrganism());
    }

    @Test
    void testChain() {
        String chain = "chain value";
        UniParcCrossReference xref = new UniParcCrossReferenceBuilder().chain(chain).build();
        assertEquals(chain, xref.getChain());
    }

    @Test
    void testNcbiGi() {
        String ncbiGi = "ncbiGi value";
        UniParcCrossReference xref = new UniParcCrossReferenceBuilder().ncbiGi(ncbiGi).build();
        assertEquals(ncbiGi, xref.getNcbiGi());
    }

    @Test
    void testProteomeIdComponentsSet() {
        String proteomeId = "proteomeId value";
        String component = "component value";
        List<ProteomeIdComponent> proteomeIdComponents = List.of(new ProteomeIdComponentBuilder().proteomeId(proteomeId).component(component).build());
        UniParcCrossReference xref =
                new UniParcCrossReferenceBuilder().proteomeIdComponentsSet(proteomeIdComponents).build();
        assertEquals(proteomeIdComponents, xref.getProteomeIdComponents());
    }

    @Test
    void testProteomeIdComponentsAdd() {
        String proteomeId = "proteomeId value";
        String component = "component value";
        List<ProteomeIdComponent> proteomeIdComponents = new ArrayList<>();
        proteomeIdComponents.add(new ProteomeIdComponentBuilder().proteomeId(proteomeId).component(component).build());
        UniParcCrossReferenceBuilder builder = new UniParcCrossReferenceBuilder().proteomeIdComponentsSet(proteomeIdComponents);
        UniParcCrossReference xref = builder.build();
        assertEquals(proteomeIdComponents, xref.getProteomeIdComponents());
        String proteomeId2 = "proteomeId value2";
        String component2 = "component value2";
        ProteomeIdComponent proteomeIdComponent2 = new ProteomeIdComponentBuilder().proteomeId(proteomeId2).component(component2).build();
        builder.proteomeIdComponentsAdd(proteomeIdComponent2);
        xref = builder.build();
        assertEquals(2, xref.getProteomeIdComponents().size());
    }

    @Test
    void testFromUniParcDBCrossReference() {
        LocalDate created = LocalDate.of(2017, 5, 17);
        LocalDate lastUpdated = LocalDate.of(2017, 2, 27);
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("prop1", "some pname"));
        properties.add(new Property("prop2", "some gname"));
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
