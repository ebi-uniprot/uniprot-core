package org.uniprot.core.citation.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Journal;

class JournalImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Journal obj = new JournalImpl();
        assertNotNull(obj);
    }

    @Test
    void toStringMethod() {
        Journal obj = new JournalImpl("name");
        assertEquals("JournalImpl{name='name'}", obj.toString());
    }
}
