package org.uniprot.core.uniprot.impl;

import org.junit.jupiter.api.Test;

import org.uniprot.core.uniprot.UniProtId;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UniProtIdImplTest {

    @Test
    void testUniProtIdImpl() {
        String val = "P12345_HUMAN";
        UniProtId uniprotId = new UniProtIdImpl(val);
        assertEquals(val, uniprotId.getValue());
    }
}
