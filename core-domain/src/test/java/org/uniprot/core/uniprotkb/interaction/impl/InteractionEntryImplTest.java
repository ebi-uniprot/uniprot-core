package org.uniprot.core.uniprotkb.interaction.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.interaction.InteractionEntry;

import static org.junit.jupiter.api.Assertions.*;

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
