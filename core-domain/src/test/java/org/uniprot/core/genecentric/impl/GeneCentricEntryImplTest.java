package org.uniprot.core.genecentric.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.genecentric.GeneCentricEntry;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lgonzales
 * @since 16/10/2020
 */
class GeneCentricEntryImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        GeneCentricEntry obj = new GeneCentricEntryImpl();
        assertNotNull(obj);
    }
}