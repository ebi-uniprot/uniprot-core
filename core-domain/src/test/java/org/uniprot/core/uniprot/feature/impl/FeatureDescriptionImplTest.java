package org.uniprot.core.uniprot.feature.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.feature.FeatureDescription;

class FeatureDescriptionImplTest {

    @Test
    void test() {
        String value = "Some description";
        FeatureDescriptionImpl description = new FeatureDescriptionImpl(value);
        assertEquals(value, description.getValue());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        FeatureDescription obj = new FeatureDescriptionImpl();
        assertNotNull(obj);
    }

    @Test
    void nullObjectNotEqual() {
        assertNotEquals(new FeatureDescriptionImpl("value"), null);
    }

    @Test
    void twoDifferentObject_sameValue() {
        assertEquals(new FeatureDescriptionImpl("value"), new FeatureDescriptionImpl("value"));
    }
}
