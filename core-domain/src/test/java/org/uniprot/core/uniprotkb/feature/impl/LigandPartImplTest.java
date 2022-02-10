package org.uniprot.core.uniprotkb.feature.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.feature.LigandPart;

/**
 * @author jluo
 * @date: 9 Feb 2022
 */
class LigandPartImplTest {

    @Test
    void testFull() {
        LigandPart ligandPart = new LigandPartImpl("aname", "aId", "alabel", "anote");
        assertEquals("aname", ligandPart.getName());
        assertEquals("aId", ligandPart.getId());
        assertEquals("alabel", ligandPart.getLabel());
        assertEquals("anote", ligandPart.getNote());
    }

    @Test
    void testMissing() {
        LigandPart ligandPart = new LigandPartImpl("aname", "aId", null, null);
        assertEquals("aname", ligandPart.getName());
        assertEquals("aId", ligandPart.getId());
        assertEquals(null, ligandPart.getLabel());
        assertEquals(null, ligandPart.getNote());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        LigandPart obj = new LigandPartImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        LigandPartImpl impl = new LigandPartImpl("aname", "aId", "alabel", "anote");
        LigandPart obj = LigandPartBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
