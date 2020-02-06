package org.uniprot.core.uniprot.description.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.description.EC;
import org.uniprot.core.uniprot.description.builder.ECBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceCode;
import org.uniprot.core.uniprot.evidence.builder.EvidenceBuilder;

class ECImplTest {

    @Test
    void testECImplComplete() {
        String ec = "4.6.1.2";
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(
                new EvidenceBuilder()
                        .evidenceCode(EvidenceCode.ECO_0000313)
                        .databaseName("Ensembl")
                        .databaseId("ENSP0001324")
                        .build());
        evidences.add(
                new EvidenceBuilder()
                        .evidenceCode(EvidenceCode.ECO_0000256)
                        .databaseName("PIRNR")
                        .databaseId("PIRNR001361")
                        .build());

        EC ecObj = new ECBuilder().value(ec).evidencesSet(evidences).build();
        assertEquals(ec, ecObj.getValue());
        assertTrue(ecObj.isValid());
        assertEquals(evidences, ecObj.getEvidences());
    }

    @Test
    void testECImplWithoutEvidences() {
        String ec = "4.6.1.2";

        EC ecObj = new ECBuilder().value(ec).build();
        assertEquals(ec, ecObj.getValue());
        assertTrue(ecObj.isValid());
        assertEquals(Collections.emptyList(), ecObj.getEvidences());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        EC obj = new ECImpl();
        assertNotNull(obj);
        assertFalse(obj.hasEvidences());
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        EC impl = new ECImpl("val", createEvidences());
        EC obj = ECBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void displayedWillAddEvidences() {
        EC impl = new ECImpl("val", createEvidences());
        assertEquals(
                "val {ECO:0000255|PROSITE-ProRule:PRU10028, ECO:0000256|PIRNR:PIRNR001361}",
                impl.getDisplayed(" "));
    }
}
