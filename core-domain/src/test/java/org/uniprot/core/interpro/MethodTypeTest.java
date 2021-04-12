package org.uniprot.core.interpro;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author jluo
 * @date: 9 Apr 2021
 */
class MethodTypeTest {

    @Test
    void testGetDbcodeName() {
        MethodType type = MethodType.HAMAP;
        assertEquals("HAMAP", type.getName());
        assertEquals("Q", type.getDbcode());
    }

    @Test
    void testTypeOfDbCode() {
        MethodType type = MethodType.typeOfDbCode("M");
        assertEquals(MethodType.PROFILE, type);
        type = MethodType.typeOfDbCode("");
        assertEquals(MethodType.PREFILE, type);
    }

    @Test
    void testTypeOf() {
        MethodType type = MethodType.typeOf("SSF");
        assertEquals(MethodType.SSF, type);
        type = MethodType.typeOf("PANTHER");
        assertEquals(MethodType.PANTHER, type);
    }
}
