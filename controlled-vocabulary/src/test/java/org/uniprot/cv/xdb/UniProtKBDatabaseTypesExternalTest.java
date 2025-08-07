package org.uniprot.cv.xdb;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.xdb.UniProtDatabaseDetail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.uniprot.cv.common.CVSystemProperties.DR_DATABASE_TYPES_LOCATION;

class UniProtKBDatabaseTypesExternalTest {


    @Test
    @Disabled
    void testWithExternalFileGetsPriority() {
        try {
            System.setProperty(DR_DATABASE_TYPES_LOCATION, "src/test/resources/xref/drlineconfigurationexternal.json");

            UniProtDatabaseDetail opType =
                    UniProtDatabaseTypes.INSTANCE.getDbTypeByName("EX");
            assertNotNull(opType);
            assertEquals("External", opType.getDisplayName());

        } finally {
            System.clearProperty(DR_DATABASE_TYPES_LOCATION);
        }
    }
}
