package org.uniprot.core.uniref.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniref.UniRefEntryId;

class UniRefEntryIdImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniRefEntryId obj = new UniRefEntryIdImpl();
        assertNotNull(obj);
    }
}
