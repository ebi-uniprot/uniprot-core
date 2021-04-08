package org.uniprot.core.cv.go;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GoAspectTest {

    @Test
    void FUNCTION() {
        assertEquals("GO Molecular Function", GoAspect.FUNCTION.getDisplayName());
    }

    @Test
    void PROCESS() {
        assertEquals("GO Biological Process", GoAspect.PROCESS.getDisplayName());
    }

    @Test
    void COMPONENT() {
        assertEquals("GO Cellular Component", GoAspect.COMPONENT.getDisplayName());
    }
    @Test
    void testTypeof() {
    	assertEquals(GoAspect.FUNCTION, GoAspect.typeOf("F"));
    	assertEquals(GoAspect.COMPONENT, GoAspect.typeOf("C"));
    	assertEquals(GoAspect.PROCESS, GoAspect.typeOf("P"));
    	assertEquals(GoAspect.FUNCTION, GoAspect.typeOf("GO Molecular Function"));
    }
}
