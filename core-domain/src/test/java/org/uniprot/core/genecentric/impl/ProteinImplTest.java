package org.uniprot.core.genecentric.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.genecentric.Protein;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lgonzales
 * @since 16/10/2020
 */
class ProteinImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Protein obj = new ProteinImpl();
        assertNotNull(obj);
    }

}