package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ECBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.builder.EvidenceBuilder;

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
