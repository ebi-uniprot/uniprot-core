package org.uniprot.core.antigen.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.antigen.AntigenFeature;

/**
 * @author lgonzales
 * @since 06/05/2020
 */
class AntigenFeatureImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        AntigenFeature obj = new AntigenFeatureImpl();
        assertNotNull(obj);
    }
}
