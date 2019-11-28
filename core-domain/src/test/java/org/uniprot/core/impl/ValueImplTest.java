package org.uniprot.core.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Value;

import static org.junit.jupiter.api.Assertions.*;

class ValueImplTest {

    @Test
    void testGetValue() {
        String val = "Some Value";
        ValueImpl valImpl = new ValueImpl(val);
        assertEquals(val, valImpl.getValue());
        ValueImpl valImpl2 = new ValueImpl(val);
        assertEquals(valImpl, valImpl2);
        ValueImpl valImpl3 = new ValueImpl("AnotherValue");
        assertNotEquals(valImpl, valImpl3);
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Value obj = new ValueImpl();
        assertNotNull(obj);
    }
}
