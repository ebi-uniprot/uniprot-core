package org.uniprot.core.uniprot.description.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.description.Flag;
import org.uniprot.core.uniprot.description.FlagType;
import org.uniprot.core.uniprot.description.builder.FlagBuilder;

import static org.junit.jupiter.api.Assertions.*;

class FlagImplTest {

    @Test
    void testFlagImpl() {
        FlagType type = FlagType.FRAGMENT;
        Flag flag = new FlagImpl(type);
        assertEquals(type, flag.getType());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Flag obj = new FlagImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        Flag impl = new FlagImpl(FlagType.FRAGMENT);
        Flag obj = new FlagBuilder(FlagType.FRAGMENT).from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
