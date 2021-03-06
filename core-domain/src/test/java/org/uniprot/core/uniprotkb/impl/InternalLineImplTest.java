package org.uniprot.core.uniprotkb.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.InternalLine;
import org.uniprot.core.uniprotkb.InternalLineType;

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
