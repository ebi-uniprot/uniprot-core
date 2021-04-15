package org.uniprot.core.interpro.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author jluo
 * @date: 9 Apr 2021
 */
class InterProMatchImplTest {

    @Test
    void testDefaultConstructor() {
        InterProMatchImpl obj = new InterProMatchImpl();
        assertNotNull(obj);
    }
}
