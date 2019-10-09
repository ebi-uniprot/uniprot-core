package org.uniprot.core.uniprot.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.SourceLine;
import org.uniprot.core.uniprot.builder.SourceLineBuilder;

class SourceLineImplTest {
    private final String VAL = "val";

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        SourceLine obj = new SourceLineImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        SourceLineImpl impl = new SourceLineImpl(VAL);
        SourceLine obj = new SourceLineBuilder(VAL).from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
