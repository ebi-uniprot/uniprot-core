package org.uniprot.core.interpro.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author jluo
 * @date: 12 Apr 2021
 */
class InterProGroupImplTest {
    @Test
    void testDefaultConstructor() {
        InterProGroupImpl obj = new InterProGroupImpl();
        assertNotNull(obj);
    }
}
