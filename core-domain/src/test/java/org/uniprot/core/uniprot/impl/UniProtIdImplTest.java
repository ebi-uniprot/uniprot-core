package org.uniprot.core.uniprot.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.UniProtId;

class UniProtIdImplTest {

    @Test
    void testUniProtIdImpl() {
        String val = "P12345_HUMAN";
        UniProtId uniprotId = new UniProtIdImpl(val);
        assertEquals(val, uniprotId.getValue());
    }
}
