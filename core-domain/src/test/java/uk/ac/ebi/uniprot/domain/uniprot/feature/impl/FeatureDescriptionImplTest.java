package uk.ac.ebi.uniprot.domain.uniprot.feature.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.TestHelper;

class FeatureDescriptionImplTest {

	@Test
	void test() {
		String value = "Some description";
		FeatureDescriptionImpl description = new FeatureDescriptionImpl(value);
		assertEquals(value, description.getValue());
		TestHelper.verifyJson(description);
	}

}
