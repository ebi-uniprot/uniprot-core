package org.uniprot.core.uniprot.feature.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FeatureDescriptionImplTest {

    @Test
    void test() {
        String value = "Some description";
        FeatureDescriptionImpl description = new FeatureDescriptionImpl(value);
        assertEquals(value, description.getValue());
    }
}
