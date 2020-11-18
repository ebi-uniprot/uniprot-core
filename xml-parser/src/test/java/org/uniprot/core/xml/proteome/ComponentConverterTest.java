package org.uniprot.core.xml.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.proteome.Component;
import org.uniprot.core.proteome.GenomeAnnotation;
import org.uniprot.core.proteome.ProteomeDatabase;
import org.uniprot.core.proteome.impl.ComponentBuilder;
import org.uniprot.core.proteome.impl.GenomeAnnotationBuilder;
import org.uniprot.core.xml.jaxb.proteome.ComponentType;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;

class ComponentConverterTest {
    private final ObjectFactory xmlFactory = new ObjectFactory();
    ComponentConverter converter = new ComponentConverter();

    @Test
    void testFromXml() {
        ComponentType xmlObj = xmlFactory.createComponentType();
        xmlObj.setName("component name");
        xmlObj.setDescription("component description");
        Component component = converter.fromXml(xmlObj);
        assertEquals("component name", component.getName());
        assertEquals("component description", component.getDescription());
        assertEquals(0, component.getProteomeCrossReferences().size());
    }

    @Test
    void testToXml() {
        CrossReference<ProteomeDatabase> bioSample =
                new CrossReferenceBuilder<ProteomeDatabase>()
                        .database(ProteomeDatabase.BIOSAMPLE)
                        .id("bio Value")
                        .build();

        CrossReference<ProteomeDatabase> genomeAccession =
                new CrossReferenceBuilder<ProteomeDatabase>()
                        .database(ProteomeDatabase.GENOME_ACCESSION)
                        .id("genome Value")
                        .build();

        GenomeAnnotation genomeAnnotation =
                new GenomeAnnotationBuilder().source("source value").url("url value").build();

        Component component =
                new ComponentBuilder()
                        .name("some name")
                        .description("some description")
                        .genomeAnnotation(genomeAnnotation)
                        .proteomeCrossReferencesAdd(genomeAccession)
                        .proteomeCrossReferencesAdd(bioSample)
                        .build();
        ComponentType xmlObj = converter.toXml(component);
        Component converted = converter.fromXml(xmlObj);
        assertEquals(component, converted);
    }

    @Test
    void testEmptyComponent() {
        Component component = new ComponentBuilder().build();
        ComponentType xmlObj = converter.toXml(component);
        assertNull(xmlObj.getDescription());
        assertNull(xmlObj.getName());
        assertNull(xmlObj.getBiosampleId());
        assertNotNull(xmlObj.getGenomeAccession());
        assertEquals(0, xmlObj.getGenomeAccession().size());
    }
}
