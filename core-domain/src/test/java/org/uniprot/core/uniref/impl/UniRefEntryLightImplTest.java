package org.uniprot.core.uniref.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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
        assertTrue(obj.getOrganisms().isEmpty());
    }
}
