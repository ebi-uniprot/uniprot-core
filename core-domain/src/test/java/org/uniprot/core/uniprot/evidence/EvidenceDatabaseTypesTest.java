package org.uniprot.core.uniprot.evidence;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * @author jluo
 * @date: 30 Aug 2019
 */
class EvidenceDatabaseTypesTest {

    @Test
    void testGetType() {
        String name = "EMBL";
        EvidenceDatabaseDetail emblEvidence = EvidenceDatabaseTypes.INSTANCE.getType(name);
        assertNotNull(emblEvidence);
    }

    @Test
    void IllegalArgumentExceptionWithTypeNotFound() {
        assertThrows(IllegalArgumentException.class, () -> EvidenceDatabaseTypes.INSTANCE.getType(null));
    }
}
