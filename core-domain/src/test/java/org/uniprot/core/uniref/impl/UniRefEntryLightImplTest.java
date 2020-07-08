package org.uniprot.core.uniref.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniref.UniRefEntry;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lgonzales
 * @since 08/07/2020
 */
class UniRefEntryLightImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniRefEntryLightImpl obj = new UniRefEntryLightImpl();
        assertNotNull(obj);
        assertTrue(obj.getMembers().isEmpty());
        assertTrue(obj.getMemberIdTypes().isEmpty());
        assertTrue(obj.getOrganismIds().isEmpty());
        assertTrue(obj.getOrganisms().isEmpty());
    }

}