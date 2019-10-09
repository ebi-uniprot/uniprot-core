package org.uniprot.core.uniref.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniref.GoTerm;

class GoTermImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        GoTerm obj = new GoTermImpl();
        assertNotNull(obj);
    }
}
