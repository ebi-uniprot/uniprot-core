package org.uniprot.core.xml.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.Component;
import org.uniprot.core.proteome.builder.ComponentBuilder;
import org.uniprot.core.xml.jaxb.proteome.ComponentType;
import org.uniprot.core.xml.jaxb.proteome.ComponentTypeType;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;
import org.uniprot.core.xml.proteome.ComponentConverter;

class ComponentConverterTest {
	private ObjectFactory xmlFactory =new ObjectFactory();
	ComponentConverter converter = new ComponentConverter();
	@Test
	void testFromXml() {
		ComponentType xmlObj = xmlFactory.createComponentType();
		xmlObj.setName("component name");
		xmlObj.setDescription("component description");
		xmlObj.setType(ComponentTypeType.CON);
		Component component = converter.fromXml(xmlObj);
		assertEquals("component name", component.getName());
		assertEquals("component description", component.getDescription());
		assertEquals(0, component.getDbXReferences().size());
		assertEquals(org.uniprot.core.proteome.ComponentType.CON, component.getType());
	}

	@Test
	void testToXml() {
		Component component = 
				ComponentBuilder.newInstance()
				.name("some name")
				.description("some description")
				.type(org.uniprot.core.proteome.ComponentType.PRIMARY)
				.build();
		ComponentType xmlObj = converter.toXml(component);
		Component converted = converter.fromXml(xmlObj);
		assertEquals(component, converted);
	}

}