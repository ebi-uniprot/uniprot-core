package org.uniprot.core.uniprot.feature.impl;

import org.junit.jupiter.api.Test;

import org.uniprot.core.uniprot.feature.impl.FeatureDescriptionImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FeatureDescriptionImplTest {

    @Test
    void test() {
        String value = "Some description";
        FeatureDescriptionImpl description = new FeatureDescriptionImpl(value);
        assertEquals(value, description.getValue());
    }

}
