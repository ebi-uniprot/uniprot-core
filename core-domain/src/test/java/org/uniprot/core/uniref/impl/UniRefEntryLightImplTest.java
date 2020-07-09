package org.uniprot.core.uniref.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniref.UniRefEntryLight;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.uniprot.core.uniref.impl.UniRefEntryLightImpl.NAME_PREFIX;

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
