package org.uniprot.core.uniprot.evidence;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
}
