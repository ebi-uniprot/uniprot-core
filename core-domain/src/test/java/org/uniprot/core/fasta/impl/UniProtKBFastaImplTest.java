package org.uniprot.core.fasta.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.fasta.UniProtKBFasta;

/**
 * @author lgonzales
 * @since 22/10/2020
 */
class UniProtKBFastaImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniProtKBFasta obj = new UniProtKBFastaImpl();
        assertNotNull(obj);
    }
}
