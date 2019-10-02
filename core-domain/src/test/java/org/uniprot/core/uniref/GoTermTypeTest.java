package org.uniprot.core.uniref;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GoTermTypeTest {

    @Test
    void FUNCTION() {
        assertEquals("GO Molecular Function", GoTermType.FUNCTION.toDisplayName());
    }

    @Test
    void PROCESS() {
        assertEquals("GO Biological Process", GoTermType.PROCESS.toDisplayName());
    }

    @Test
    void COMPONENT() {
        assertEquals("GO Cellular Component", GoTermType.COMPONENT.toDisplayName());
    }
}
