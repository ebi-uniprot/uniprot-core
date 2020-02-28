package org.uniprot.core.proteome.builder;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.ObjectsForTests.proteomeXReferenceTypes;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.proteome.Component;
import org.uniprot.core.proteome.ComponentType;
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
                        .type(ComponentType.PRIMARY)
                        .build();
        assertEquals("someName", component.getName());
        assertEquals("some description", component.getDescription());
        assertEquals(102, component.getProteinCount());
        assertEquals(ComponentType.PRIMARY, component.getType());
    }

    @Test
    void testAddXref() {
        DBCrossReference<ProteomeDatabase> xref1 =
                new DBCrossReferenceBuilder<ProteomeDatabase>()
                        .databaseType(ProteomeDatabase.GENOME_ACCESSION)
                        .id("ACA121")
                        .build();
        Component component =
                new ComponentBuilder()
                        .dbXReferencesAdd(proteomeXReferenceTypes().get(0))
                        .dbXReferencesAdd(proteomeXReferenceTypes().get(1))
                        .build();
        assertEquals(2, component.getDbXReferences().size());
        assertThat(component.getDbXReferences(), hasItem(xref1));
    }

    @Test
    void testXrefs() {
        DBCrossReference<ProteomeDatabase> xref2 =
                new DBCrossReferenceBuilder<ProteomeDatabase>()
                        .databaseType(ProteomeDatabase.GENOME_ANNOTATION)
                        .id("ADFDA121")
                        .build();
        Component component =
                new ComponentBuilder().dbXReferencesSet(proteomeXReferenceTypes()).build();
        assertEquals(2, component.getDbXReferences().size());
        assertThat(component.getDbXReferences(), hasItem(xref2));
    }
}
