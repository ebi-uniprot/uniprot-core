package org.uniprot.core.uniprot.description.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.description.Flag;
import org.uniprot.core.uniprot.description.FlagType;

class FlagBuilderTest {

    @Test
    void canCreateBuilderFromInstance() {
        Flag obj = new FlagBuilder(FlagType.FRAGMENT).build();
        FlagBuilder builder = new FlagBuilder(FlagType.FRAGMENT_PRECURSOR).from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        Flag obj = new FlagBuilder(FlagType.FRAGMENT).build();
        Flag obj2 = new FlagBuilder(FlagType.FRAGMENT).build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
