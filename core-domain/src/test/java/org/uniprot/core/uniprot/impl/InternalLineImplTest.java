package org.uniprot.core.uniprot.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.InternalLine;
import org.uniprot.core.uniprot.InternalLineType;
import org.uniprot.core.uniprot.builder.InternalLineBuilder;

class InternalLineImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        InternalLine obj = new InternalLineImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        InternalLineImpl impl = new InternalLineImpl(InternalLineType.EV, "def");
        InternalLine obj = InternalLineBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
