package uk.ac.ebi.uniprot.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.Property;

class PropertyTest {

	@Test
	void testProperty() {
		Property pt = new Property("some key", "some value");
		assertEquals("some key", pt.getKey());
		assertEquals("some value", pt.getValue());
		TestHelper.verifyJson(pt);
		
	}

}
