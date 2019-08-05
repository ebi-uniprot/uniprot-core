package org.uniprot.core;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Property;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PropertyTest {

    @Test
    void testProperty() {
        Property pt = new Property("some key", "some value");
        assertEquals("some key", pt.getKey());
        assertEquals("some value", pt.getValue());
        TestHelper.verifyJson(pt);

    }

}
