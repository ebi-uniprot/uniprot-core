package org.uniprot.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PropertyTest {

    @Test
    void testProperty() {
        Property pt = new Property("some key", "some value");
        assertEquals("some key", pt.getKey());
        assertEquals("some value", pt.getValue());
    }
}
