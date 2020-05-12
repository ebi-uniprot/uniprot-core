package org.uniprot.core.uniprotkb.feature.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.feature.FeatureId;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;

class FeatureIdImplTest {

    @Test
    void testFeatureIdImpl() {
        String value = "PRO_123";
        FeatureIdImpl featureId = new FeatureIdImpl(value);
        assertEquals(value, featureId.getValue());
    }

    @Test
    void testIsValidPRO() {
        String value = "PRO_123";
        FeatureIdImpl featureId = new FeatureIdImpl(value);
        assertTrue(featureId.isValid(UniprotKBFeatureType.CHAIN));
        assertTrue(featureId.isValid(UniprotKBFeatureType.PEPTIDE));
        assertTrue(featureId.isValid(UniprotKBFeatureType.PROPEP));
        assertFalse(featureId.isValid(UniprotKBFeatureType.VARIANT));
        assertTrue(featureId.isValid(UniprotKBFeatureType.CARBOHYD));
    }

    @Test
    void testIsValidVAR() {
        String value = "VAR_123";
        FeatureIdImpl featureId = new FeatureIdImpl(value);
        assertFalse(featureId.isValid(UniprotKBFeatureType.PROPEP));
        assertTrue(featureId.isValid(UniprotKBFeatureType.VARIANT));
    }

    @Test
    void testIsValidVSP() {
        String value = "VSP_123";
        FeatureIdImpl featureId = new FeatureIdImpl(value);
        assertFalse(featureId.isValid(UniprotKBFeatureType.PROPEP));
        assertFalse(featureId.isValid(UniprotKBFeatureType.VARIANT));
        assertTrue(featureId.isValid(UniprotKBFeatureType.VAR_SEQ));
    }

    @Test
    void testIsValidCAR() {
        String value = "CAR_000083";
        FeatureIdImpl featureId = new FeatureIdImpl(value);

        assertFalse(featureId.isValid(UniprotKBFeatureType.PROPEP));
        assertFalse(featureId.isValid(UniprotKBFeatureType.VARIANT));
        assertTrue(featureId.isValid(UniprotKBFeatureType.CARBOHYD));
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        FeatureId obj = new FeatureIdImpl();
        assertNotNull(obj);
        assertFalse(obj.hasValue());
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        FeatureIdImpl impl = new FeatureIdImpl("2");
        FeatureId obj = FeatureIdBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
