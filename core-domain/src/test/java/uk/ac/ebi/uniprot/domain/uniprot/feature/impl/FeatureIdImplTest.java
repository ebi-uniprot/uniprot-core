package uk.ac.ebi.uniprot.domain.uniprot.feature.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;

class FeatureIdImplTest {

	@Test
	void testFeatureIdImpl() {
		String value = "PRO_123";
		FeatureIdImpl featureId = new FeatureIdImpl(value);
		assertEquals(value, featureId.getValue());
	}

	@Test
	void testJsonConversion() {
		String value = "PRO_123";
		FeatureIdImpl featureId = new FeatureIdImpl(value);
		TestHelper.verifyJson(featureId);
	}
	@Test
	void testIsValid() {
		String value = "PRO_123";
		FeatureIdImpl featureId = new FeatureIdImpl(value);
		assertTrue(featureId.isValid(FeatureType.CHAIN));
		assertTrue(featureId.isValid(FeatureType.PEPTIDE));
		assertTrue(featureId.isValid(FeatureType.PROPEP));
		assertFalse(featureId.isValid(FeatureType.VARIANT));
		
		value = "VAR_123";
		featureId = new FeatureIdImpl(value);
		assertFalse(featureId.isValid(FeatureType.PROPEP));
		assertTrue(featureId.isValid(FeatureType.VARIANT));
		
		value = "VSP_123";
		featureId = new FeatureIdImpl(value);
		assertFalse(featureId.isValid(FeatureType.PROPEP));
		assertFalse(featureId.isValid(FeatureType.VARIANT));
		assertTrue(featureId.isValid(FeatureType.VAR_SEQ));
	}

}
