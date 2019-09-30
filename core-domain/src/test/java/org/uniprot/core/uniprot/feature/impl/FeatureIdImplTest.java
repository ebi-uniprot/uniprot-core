package org.uniprot.core.uniprot.feature.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.feature.FeatureType;

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
    }

    @Test
    void testIsValidPRO() {
        String value = "PRO_123";
        FeatureIdImpl featureId = new FeatureIdImpl(value);
        assertTrue(featureId.isValid(FeatureType.CHAIN));
        assertTrue(featureId.isValid(FeatureType.PEPTIDE));
        assertTrue(featureId.isValid(FeatureType.PROPEP));
        assertFalse(featureId.isValid(FeatureType.VARIANT));
        assertTrue(featureId.isValid(FeatureType.CARBOHYD));
    }

    @Test
    void testIsValidVAR() {
        String value = "VAR_123";
        FeatureIdImpl featureId = new FeatureIdImpl(value);
        assertFalse(featureId.isValid(FeatureType.PROPEP));
        assertTrue(featureId.isValid(FeatureType.VARIANT));
    }

    @Test
    void testIsValidVSP() {
        String value = "VSP_123";
        FeatureIdImpl featureId = new FeatureIdImpl(value);
        assertFalse(featureId.isValid(FeatureType.PROPEP));
        assertFalse(featureId.isValid(FeatureType.VARIANT));
        assertTrue(featureId.isValid(FeatureType.VAR_SEQ));
    }

    @Test
    void testIsValidCAR() {
        String value = "CAR_000083";
        FeatureIdImpl featureId = new FeatureIdImpl(value);

        assertFalse(featureId.isValid(FeatureType.PROPEP));
        assertFalse(featureId.isValid(FeatureType.VARIANT));
        assertTrue(featureId.isValid(FeatureType.CARBOHYD));
    }
}
