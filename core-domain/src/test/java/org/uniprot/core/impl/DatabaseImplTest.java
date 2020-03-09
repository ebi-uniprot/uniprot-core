package org.uniprot.core.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Database;

class DatabaseImplTest {
    @Test
    void test() {
        Database type = new DefaultDatabase("EMBL");
        assertEquals("EMBL", type.getName());
    }
}
