package org.uniprot.core.uniprot.description.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.description.Flag;
import org.uniprot.core.uniprot.description.FlagType;

class FlagImplTest {

    @Test
    void testFlagImpl() {
        FlagType type = FlagType.FRAGMENT;
        Flag flag = new FlagImpl(type);
        assertEquals(type, flag.getType());
    }
}
