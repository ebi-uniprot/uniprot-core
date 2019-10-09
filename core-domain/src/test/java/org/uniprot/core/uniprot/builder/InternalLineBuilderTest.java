package org.uniprot.core.uniprot.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.InternalLine;
import org.uniprot.core.uniprot.InternalLineType;

class InternalLineBuilderTest {
    @Test
    void canCreateBuilderFromInstance() {
        InternalLine obj = new InternalLineBuilder(InternalLineType.PE, "abc").build();
        InternalLineBuilder builder = new InternalLineBuilder(InternalLineType.PE, "abc").from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        InternalLine obj = new InternalLineBuilder(InternalLineType.PE, "abc").build();
        InternalLine obj2 = new InternalLineBuilder(InternalLineType.PE, "abc").build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
