package org.uniprot.core.uniprotkb.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.GeneEncodingType;
import org.uniprot.core.uniprotkb.GeneLocation;
import org.uniprot.core.uniprotkb.evidence.Evidence;

import java.util.List;

class GeneLocationImplTest {

    @Test
    void testGetDisplayedValueAPICOPLAST_PLASTID() {
        GeneEncodingType type = GeneEncodingType.APICOPLAST;
        String val = "";
        List<Evidence> evidences = createEvidences();
        GeneLocation geneLocation = new GeneLocationImpl(type, val, evidences);
        String expected =
                "Plastid; Apicoplast {ECO:0000255|PROSITE-ProRule:PRU10028,"
                        + " ECO:0000256|PIRNR:PIRNR001361}";
        assertEquals(expected, geneLocation.getDisplayed(""));
    }

    @Test
    void testGetDisplayedValueMITOCHONDRION() {
        GeneEncodingType type = GeneEncodingType.MITOCHONDRION;
        String val = "some value";
        List<Evidence> evidences = createEvidences();
        GeneLocation geneLocation = new GeneLocationImpl(type, val, evidences);
        String expected =
                "Mitochondrion {ECO:0000255|PROSITE-ProRule:PRU10028,"
                        + " ECO:0000256|PIRNR:PIRNR001361}";
        assertEquals(expected, geneLocation.getDisplayed(""));
    }

    @Test
    void testGetDisplayedValueHYDROGENOSOME() {
        GeneEncodingType type = GeneEncodingType.HYDROGENOSOME;
        String val = "some value";
        List<Evidence> evidences = createEvidences();
        GeneLocation geneLocation = new GeneLocationImpl(type, val, evidences);
        String expected =
                "Hydrogenosome {ECO:0000255|PROSITE-ProRule:PRU10028,"
                        + " ECO:0000256|PIRNR:PIRNR001361}";
        assertEquals(expected, geneLocation.getDisplayed(""));
    }

    @Test
    void testGetDisplayedValuePLASTID() {
        GeneEncodingType type = GeneEncodingType.PLASTID;
        String val = "some value";
        List<Evidence> evidences = createEvidences();
        GeneLocation geneLocation = new GeneLocationImpl(type, val, evidences);
        String expected =
                "Plastid {ECO:0000255|PROSITE-ProRule:PRU10028, ECO:0000256|PIRNR:PIRNR001361}";
        assertEquals(expected, geneLocation.getDisplayed(""));
    }

    @Test
    void testGetDisplayedValuePLASMID() {
        GeneEncodingType type = GeneEncodingType.PLASMID;
        String val = "some value";
        List<Evidence> evidences = createEvidences();
        GeneLocation geneLocation = new GeneLocationImpl(type, val, evidences);
        String expected =
                "Plasmid some value {ECO:0000255|PROSITE-ProRule:PRU10028,"
                        + " ECO:0000256|PIRNR:PIRNR001361}";
        assertEquals(expected, geneLocation.getDisplayed(""));
    }

    @Test
    void testGeneLocationImpl() {
        GeneEncodingType type = GeneEncodingType.APICOPLAST;
        String val = "";
        List<Evidence> evidences = null;
        GeneLocation geneLocation = new GeneLocationImpl(type, val, evidences);
        assertEquals(type, geneLocation.getGeneEncodingType());
        assertEquals(val, geneLocation.getValue());
        assertTrue(geneLocation.getEvidences().isEmpty());
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        GeneLocation impl =
                new GeneLocationImpl(GeneEncodingType.APICOPLAST, "val", createEvidences());
        GeneLocation obj = GeneLocationBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        GeneLocation obj = new GeneLocationImpl();
        assertNotNull(obj);
        assertEquals("", obj.getValue());
        assertTrue(obj.getEvidences().isEmpty());
    }
}
