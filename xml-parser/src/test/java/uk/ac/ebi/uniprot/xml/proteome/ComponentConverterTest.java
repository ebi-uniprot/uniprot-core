package uk.ac.ebi.uniprot.xml.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.proteome.Component;
import uk.ac.ebi.uniprot.domain.proteome.builder.ComponentBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.ComponentType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.ObjectFactory;

class ComponentConverterTest {
	private ObjectFactory xmlFactory =new ObjectFactory();
	ComponentConverter converter = new ComponentConverter();
	@Test
	void testFromXml() {
		ComponentType xmlObj = xmlFactory.createComponentType();
		xmlObj.setName("component name");
		xmlObj.setDescription("component description");
		Component component = converter.fromXml(xmlObj);
		assertEquals("component name", component.getName());
		assertEquals("component description", component.getDescription());
		assertEquals(0, component.getDbXReferences().size());
	}

	@Test
	void testToXml() {
		Component component = 
				ComponentBuilder.newInstance()
				.name("some name")
				.description("some description")
				.build();
		ComponentType xmlObj = converter.toXml(component);
		Component converted = converter.fromXml(xmlObj);
		assertEquals(component, converted);
	}

}
