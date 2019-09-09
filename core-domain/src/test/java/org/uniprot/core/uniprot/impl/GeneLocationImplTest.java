package org.uniprot.core.uniprot.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.TestHelper;
import org.uniprot.core.uniprot.GeneEncodingType;
import org.uniprot.core.uniprot.GeneLocation;
import org.uniprot.core.uniprot.evidence.Evidence;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.uniprot.core.uniprot.EvidenceHelper.createEvidences;

public class GeneLocationImplTest {

    @Test
    public void testGetDisplayedValueAPICOPLAST_PLASTID() {
        GeneEncodingType type = GeneEncodingType.APICOPLAST_PLASTID;
        String val = "";
        List<Evidence> evidences = createEvidences();
        GeneLocation geneLocation = new GeneLocationImpl(type, val, evidences);
        String expected = "Plastid; Apicoplast {ECO:0000255|PROSITE-ProRule:PRU10028, ECO:0000256|PIRNR:PIRNR001361}";
        assertEquals(expected, geneLocation.getDisplayed(""));
        TestHelper.verifyJson(geneLocation);
    }

    @Test
    public void testGetDisplayedValueMITOCHONDRION() {
        GeneEncodingType type = GeneEncodingType.MITOCHONDRION;
        String val = "some value";
        List<Evidence> evidences = createEvidences();
        GeneLocation geneLocation = new GeneLocationImpl(type, val, evidences);
        String expected = "Mitochondrion {ECO:0000255|PROSITE-ProRule:PRU10028, ECO:0000256|PIRNR:PIRNR001361}";
        assertEquals(expected, geneLocation.getDisplayed(""));
        TestHelper.verifyJson(geneLocation);
    }

    @Test
    public void testGetDisplayedValueHYDROGENOSOME() {
        GeneEncodingType type = GeneEncodingType.HYDROGENOSOME;
        String val = "some value";
        List<Evidence> evidences = createEvidences();
        GeneLocation geneLocation = new GeneLocationImpl(type, val, evidences);
        String expected = "Hydrogenosome {ECO:0000255|PROSITE-ProRule:PRU10028, ECO:0000256|PIRNR:PIRNR001361}";
        assertEquals(expected, geneLocation.getDisplayed(""));
        TestHelper.verifyJson(geneLocation);
    }

    @Test
    public void testGetDisplayedValuePLASTID() {
        GeneEncodingType type = GeneEncodingType.PLASTID;
        String val = "some value";
        List<Evidence> evidences = createEvidences();
        GeneLocation geneLocation = new GeneLocationImpl(type, val, evidences);
        String expected = "Plastid {ECO:0000255|PROSITE-ProRule:PRU10028, ECO:0000256|PIRNR:PIRNR001361}";
        assertEquals(expected, geneLocation.getDisplayed(""));
        TestHelper.verifyJson(geneLocation);
    }

    @Test
    public void testGetDisplayedValuePLASMID() {
        GeneEncodingType type = GeneEncodingType.PLASMID;
        String val = "some value";
        List<Evidence> evidences = createEvidences();
        GeneLocation geneLocation = new GeneLocationImpl(type, val, evidences);
        String expected = "Plasmid some value {ECO:0000255|PROSITE-ProRule:PRU10028, ECO:0000256|PIRNR:PIRNR001361}";
        assertEquals(expected, geneLocation.getDisplayed(""));
        TestHelper.verifyJson(geneLocation);
    }

    @Test
    public void testGeneLocationImpl() {
        GeneEncodingType type = GeneEncodingType.APICOPLAST_PLASTID;
        String val = "";
        List<Evidence> evidences = null;
        GeneLocation geneLocation = new GeneLocationImpl(type, val, evidences);
        assertEquals(type, geneLocation.getGeneEncodingType());
        assertEquals(val, geneLocation.getValue());
        assertTrue(geneLocation.getEvidences().isEmpty());
        TestHelper.verifyJson(geneLocation);
    }
}
