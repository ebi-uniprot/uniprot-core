package org.uniprot.core.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DatabaseType;
import org.uniprot.core.TestHelper;
import org.uniprot.core.impl.DefaultDatabaseType;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DatabaseTypeImplTest {
    @Test
    void test() {
        DatabaseType type = new DefaultDatabaseType("EMBL");
        assertEquals("EMBL", type.getName());
        TestHelper.verifyJson(type);
    }
}
