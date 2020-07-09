package org.uniprot.core.uniprotkb.interaction.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.interaction.InteractionEntry;

/**
 * Created 08/07/2020
 *
 * @author Edd
 */
class InteractionEntryImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        InteractionEntry obj = new InteractionEntryImpl();
        assertNotNull(obj);
    }

    @Test
    void checkEquals() {
        assertEquals(new InteractionEntryImpl(), new InteractionEntryImpl());
    }
}
