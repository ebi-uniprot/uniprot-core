package org.uniprot.core.uniparc.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.UniParcId;

/**
 * @author jluo
 * @date: 23 May 2019
 */
class UniParcIdBuilderTest {

    @Test
    void testBuildValid() {
        String upi = "UPI0000083A08";
        UniParcId id = new UniParcIdBuilder(upi).build();
        assertEquals(upi, id.getValue());
        assertTrue(id.isValidId());
        String upi2 = "UPI0000083A02";
        UniParcId fromId = UniParcIdBuilder.from(id).build();
        assertEquals(id, fromId);
    }

    @Test
    void testBuildInValid() {
        String upi = "UPI0000083A0";
        UniParcId id = new UniParcIdBuilder(upi).build();
        assertEquals(upi, id.getValue());
        assertFalse(id.isValidId());
    }
}
