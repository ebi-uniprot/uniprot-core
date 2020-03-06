package org.uniprot.core.uniprot.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.SourceLine;

class SourceLineBuilderTest {
    @Test
    void canCreateBuilderFromInstance() {
        SourceLine obj = new SourceLineBuilder("val").build();
        SourceLineBuilder builder = SourceLineBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        SourceLine obj = new SourceLineBuilder("val").build();
        SourceLine obj2 = new SourceLineBuilder("val").build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
