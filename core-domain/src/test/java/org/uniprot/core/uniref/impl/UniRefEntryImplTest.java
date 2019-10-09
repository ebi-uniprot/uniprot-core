package org.uniprot.core.uniref.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniref.UniRefEntry;

class UniRefEntryImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniRefEntry obj = new UniRefEntryImpl();
        assertNotNull(obj);
        assertTrue(obj.getGoTerms().isEmpty());
        assertTrue(obj.getMembers().isEmpty());
    }
}
