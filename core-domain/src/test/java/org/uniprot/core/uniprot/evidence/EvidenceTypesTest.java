package org.uniprot.core.uniprot.evidence;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * @author jluo
 * @date: 30 Aug 2019
 */
class EvidenceTypesTest {

    @Test
    void testGetType() {
        String name = "EMBL";
        EvidenceTypeDetail emblEvidence = EvidenceTypes.INSTANCE.getType(name);
        assertNotNull(emblEvidence);
    }

    @Test
    void IllegalArgumentExceptionWithTypeNotFound() {
        assertThrows(IllegalArgumentException.class, ()->EvidenceTypes.INSTANCE.getType(null));
    }
}
