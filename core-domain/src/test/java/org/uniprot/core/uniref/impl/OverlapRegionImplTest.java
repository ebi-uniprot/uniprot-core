package org.uniprot.core.uniref.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniref.OverlapRegion;

class OverlapRegionImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        OverlapRegion obj = new OverlapRegionImpl();
        assertNotNull(obj);
    }
}
