package org.uniprot.core.xml.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.Component;
import org.uniprot.core.proteome.impl.ComponentBuilder;
import org.uniprot.core.xml.jaxb.proteome.ComponentType;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;

class ComponentConverterTest {
    private ObjectFactory xmlFactory = new ObjectFactory();
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
        Component component =
                new ComponentBuilder()
                        .name("some name")
                        .description("some description")
                        .build();
        ComponentType xmlObj = converter.toXml(component);
        Component converted = converter.fromXml(xmlObj);
        assertEquals(component, converted);
    }
}
