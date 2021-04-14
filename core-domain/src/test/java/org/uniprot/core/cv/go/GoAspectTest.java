package org.uniprot.core.cv.go;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GoAspectTest {

    @Test
    void FUNCTION() {
        assertEquals("GO Molecular Function", GoAspect.FUNCTION.getDisplayName());
        assertEquals("F", GoAspect.FUNCTION.getCode());
    }

    @Test
    void PROCESS() {
        assertEquals("GO Biological Process", GoAspect.PROCESS.getDisplayName());
        assertEquals("P", GoAspect.PROCESS.getCode());
    }

    @Test
    void COMPONENT() {
        assertEquals("GO Cellular Component", GoAspect.COMPONENT.getDisplayName());
        assertEquals("C", GoAspect.COMPONENT.getCode());
    }
    @Test
    void testTypeof() {
    	assertEquals(GoAspect.FUNCTION, GoAspect.typeOf("F"));
    	assertEquals(GoAspect.COMPONENT, GoAspect.typeOf("C"));
    	assertEquals(GoAspect.PROCESS, GoAspect.typeOf("P"));
    	assertEquals(GoAspect.FUNCTION, GoAspect.typeOf("GO Molecular Function"));
    }
}
