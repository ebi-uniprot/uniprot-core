package org.uniprot.core.proteome.impl;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.ObjectsForTests.proteomeXReferenceTypes;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.proteome.Component;
import org.uniprot.core.proteome.ProteomeDatabase;

class ComponentBuilderTest {

    @Test
    void testNameAndDescriptionAndProteinCount() {
        Component component =
                new ComponentBuilder()
                        .name("someName")
                        .description("some description")
                        .proteinCount(102)
                        .build();
        assertEquals("someName", component.getName());
        assertEquals("some description", component.getDescription());
        assertEquals(102, component.getProteinCount());
    }

    @Test
    void testNameAndDescriptionAndProteinCountAndType() {
        Component component =
                new ComponentBuilder()
                        .name("someName")
                        .description("some description")
                        .proteinCount(102)
                        .build();
        assertEquals("someName", component.getName());
        assertEquals("some description", component.getDescription());
        assertEquals(102, component.getProteinCount());
    }

    @Test
    void testAddXref() {
        CrossReference<ProteomeDatabase> xref1 =
                new CrossReferenceBuilder<ProteomeDatabase>()
                        .database(ProteomeDatabase.GENOME_ACCESSION)
                        .id("ACA121")
                        .build();
        Component component =
                new ComponentBuilder()
                        .proteomeCrossReferencesAdd(proteomeXReferenceTypes().get(0))
                        .proteomeCrossReferencesAdd(proteomeXReferenceTypes().get(1))
                        .build();
        assertEquals(2, component.getProteomeCrossReferences().size());
        assertThat(component.getProteomeCrossReferences(), hasItem(xref1));
    }

    @Test
    void testXrefs() {
        CrossReference<ProteomeDatabase> xref2 =
                new CrossReferenceBuilder<ProteomeDatabase>()
                        .database(ProteomeDatabase.GENOME_ANNOTATION)
                        .id("ADFDA121")
                        .build();
        Component component =
                new ComponentBuilder()
                        .proteomeCrossReferencesSet(proteomeXReferenceTypes())
                        .build();
        assertEquals(2, component.getProteomeCrossReferences().size());
        assertThat(component.getProteomeCrossReferences(), hasItem(xref2));
    }
}
