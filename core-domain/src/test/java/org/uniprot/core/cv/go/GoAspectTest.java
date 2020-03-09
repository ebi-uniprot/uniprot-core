package org.uniprot.core.cv.go;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GoAspectTest {

    @Test
    void FUNCTION() {
        assertEquals("GO Molecular Function", GoAspect.FUNCTION.toDisplayName());
    }

    @Test
    void PROCESS() {
        assertEquals("GO Biological Process", GoAspect.PROCESS.toDisplayName());
    }

    @Test
    void COMPONENT() {
        assertEquals("GO Cellular Component", GoAspect.COMPONENT.toDisplayName());
    }
}
