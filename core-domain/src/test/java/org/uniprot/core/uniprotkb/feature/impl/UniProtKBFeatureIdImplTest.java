package org.uniprot.core.uniprotkb.feature.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeatureId;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;

class UniProtKBFeatureIdImplTest {

    @Test
    void testFeatureIdImpl() {
        String value = "PRO_123";
        UniProtKBFeatureIdImpl featureId = new UniProtKBFeatureIdImpl(value);
        assertEquals(value, featureId.getValue());
    }

    @Test
    void testIsValidPRO() {
        String value = "PRO_123";
        UniProtKBFeatureIdImpl featureId = new UniProtKBFeatureIdImpl(value);
        assertTrue(featureId.isValid(UniprotKBFeatureType.CHAIN));
        assertTrue(featureId.isValid(UniprotKBFeatureType.PEPTIDE));
        assertTrue(featureId.isValid(UniprotKBFeatureType.PROPEP));
        assertFalse(featureId.isValid(UniprotKBFeatureType.VARIANT));
        assertTrue(featureId.isValid(UniprotKBFeatureType.CARBOHYD));
    }

    @Test
    void testIsValidVAR() {
        String value = "VAR_123";
        UniProtKBFeatureIdImpl featureId = new UniProtKBFeatureIdImpl(value);
        assertFalse(featureId.isValid(UniprotKBFeatureType.PROPEP));
        assertTrue(featureId.isValid(UniprotKBFeatureType.VARIANT));
    }

    @Test
    void testIsValidVSP() {
        String value = "VSP_123";
        UniProtKBFeatureIdImpl featureId = new UniProtKBFeatureIdImpl(value);
        assertFalse(featureId.isValid(UniprotKBFeatureType.PROPEP));
        assertFalse(featureId.isValid(UniprotKBFeatureType.VARIANT));
        assertTrue(featureId.isValid(UniprotKBFeatureType.VAR_SEQ));
    }

    @Test
    void testIsValidCAR() {
        String value = "CAR_000083";
        UniProtKBFeatureIdImpl featureId = new UniProtKBFeatureIdImpl(value);

        assertFalse(featureId.isValid(UniprotKBFeatureType.PROPEP));
        assertFalse(featureId.isValid(UniprotKBFeatureType.VARIANT));
        assertTrue(featureId.isValid(UniprotKBFeatureType.CARBOHYD));
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniProtKBFeatureId obj = new UniProtKBFeatureIdImpl();
        assertNotNull(obj);
        assertFalse(obj.hasValue());
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        UniProtKBFeatureIdImpl impl = new UniProtKBFeatureIdImpl("2");
        UniProtKBFeatureId obj = UniProtKBFeatureIdBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
