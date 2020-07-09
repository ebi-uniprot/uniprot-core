package org.uniprot.core.uniprotkb.interaction.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.interaction.InteractionMatrixItem;

/**
 * Created 08/07/2020
 *
 * @author Edd
 */
class InteractionMatrixItemImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        InteractionMatrixItem obj = new InteractionMatrixItemImpl();
        assertNotNull(obj);
    }

    @Test
    void checkEquals() {
        assertEquals(new InteractionMatrixItemImpl(), new InteractionMatrixItemImpl());
    }
}
