package org.uniprot.core.uniprot.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.UniProtEntry;

class UniProtEntryImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniProtEntry obj = new UniProtEntryImpl();
        assertNotNull(obj);
    }
}
