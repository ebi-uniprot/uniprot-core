package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.Fusion;

import java.util.Arrays;
import java.util.List;

public class FusionImplTest {

    @Test
    void testCreateObjectByNoArgConstructor() {
        Fusion fusion = new FusionImpl();
        assertNotNull(fusion);
        assertTrue(fusion.getCters().isEmpty());
        assertTrue(fusion.getNters().isEmpty());
    }

    @Test
    void testCreateObject() {
        List<String> cters = Arrays.asList("c1", "c2", "c3");
        List<String> nters = Arrays.asList("n1", "n2", "n3");
        Fusion fusion = new FusionImpl(cters, nters);
        assertNotNull(fusion);
        assertEquals(cters, fusion.getCters());
        assertEquals(nters, fusion.getNters());
    }
}
