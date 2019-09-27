package org.uniprot.core.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DatabaseType;

class DatabaseTypeImplTest {
    @Test
    void test() {
        DatabaseType type = new DefaultDatabaseType("EMBL");
        assertEquals("EMBL", type.getName());
    }
}
