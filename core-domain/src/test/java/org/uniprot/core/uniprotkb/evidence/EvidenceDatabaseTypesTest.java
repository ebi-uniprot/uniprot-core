package org.uniprot.core.uniprotkb.evidence;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
    void testGetAllEvidenceDatabases() {
        List<EvidenceDatabaseDetail> evidenceDatabases =
                EvidenceDatabaseTypes.INSTANCE.getAllEvidenceDatabases();
        assertNotNull(evidenceDatabases);
        assertEquals(51, evidenceDatabases.size());
    }

    @Test
    void IllegalArgumentExceptionWithTypeNotFound() {
        assertThrows(
                IllegalArgumentException.class, () -> EvidenceDatabaseTypes.INSTANCE.getType(null));
    }
}
