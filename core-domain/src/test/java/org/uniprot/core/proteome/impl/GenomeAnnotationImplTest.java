package org.uniprot.core.proteome.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.GenomeAnnotation;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lgonzales
 * @since 16/11/2020
 */
class GenomeAnnotationImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        GenomeAnnotation obj = new GenomeAnnotationImpl();
        assertNotNull(obj);
    }
}