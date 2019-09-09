package org.uniprot.core.uniprot.description.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.description.Flag;
import org.uniprot.core.uniprot.description.FlagType;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FlagImplTest {

    @Test
    void testFlagImpl() {
        FlagType type = FlagType.FRAGMENT;
        Flag flag = new FlagImpl(type);
        assertEquals(type, flag.getType());
    }

}
