package org.uniprot.core.uniprot.description.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.TestHelper;
import org.uniprot.core.uniprot.description.EC;
import org.uniprot.core.uniprot.description.builder.ECBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceCode;
import org.uniprot.core.uniprot.evidence.builder.EvidenceBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ECImplTest {

    @Test
    void testECImplComplete() {
        String ec = "4.6.1.2";
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(new EvidenceBuilder().evidenceCode(EvidenceCode.ECO_0000313)
                        .databaseName("Ensembl").databaseId("ENSP0001324").build());
        evidences.add(
                new EvidenceBuilder().evidenceCode(EvidenceCode.ECO_0000256)
                        .databaseName("PIRNR").databaseId("PIRNR001361").build());

        EC ecObj = new ECBuilder().value(ec).evidences(evidences).build();
        assertEquals(ec, ecObj.getValue());
        assertTrue(ecObj.isValid());
        assertEquals(evidences, ecObj.getEvidences());
        TestHelper.verifyJson(ecObj);
    }

    @Test
    void testECImplWithoutEvidences() {
        String ec = "4.6.1.2";

        EC ecObj = new ECBuilder().value(ec).build();
        assertEquals(ec, ecObj.getValue());
        assertTrue(ecObj.isValid());
        assertEquals(Collections.emptyList(), ecObj.getEvidences());
        TestHelper.verifyJson(ecObj);
    }

}
