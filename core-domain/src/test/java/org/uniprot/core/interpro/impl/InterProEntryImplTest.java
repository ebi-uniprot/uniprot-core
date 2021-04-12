package org.uniprot.core.interpro.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author jluo
 * @date: 12 Apr 2021
 */
class InterProEntryImplTest {
    @Test
    void testDefaultConstructor() {
        InterProEntryImpl obj = new InterProEntryImpl();
        assertNotNull(obj);
    }
}
