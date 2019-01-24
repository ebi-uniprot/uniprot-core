package uk.ac.ebi.uniprot.domain.uniprot.impl;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.GeneEncodingType;
import uk.ac.ebi.uniprot.domain.uniprot.Organelle;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static uk.ac.ebi.uniprot.domain.uniprot.EvidenceHelper.createEvidences;

public class OrganelleImplTest {

    @Test
    public void testGetDisplayedValueAPICOPLAST_PLASTID() {
        GeneEncodingType type = GeneEncodingType.APICOPLAST_PLASTID;
        String val = "";
        List<Evidence> evidences = createEvidences();
        Organelle organelle = new OrganelleImpl(type, val, evidences);
        String expected = "Plastid; Apicoplast {ECO:0000255|PROSITE-ProRule:PRU10028, ECO:0000256|PIRNR:PIRNR001361}";
        assertEquals(expected, organelle.getDisplayed(""));
        TestHelper.verifyJson(organelle);
    }

    @Test
    public void testGetDisplayedValueMITOCHONDRION() {
        GeneEncodingType type = GeneEncodingType.MITOCHONDRION;
        String val = "some value";
        List<Evidence> evidences = createEvidences();
        Organelle organelle = new OrganelleImpl(type, val, evidences);
        String expected = "Mitochondrion {ECO:0000255|PROSITE-ProRule:PRU10028, ECO:0000256|PIRNR:PIRNR001361}";
        assertEquals(expected, organelle.getDisplayed(""));
        TestHelper.verifyJson(organelle);
    }

    @Test
    public void testGetDisplayedValueHYDROGENOSOME() {
        GeneEncodingType type = GeneEncodingType.HYDROGENOSOME;
        String val = "some value";
        List<Evidence> evidences = createEvidences();
        Organelle organelle = new OrganelleImpl(type, val, evidences);
        String expected = "Hydrogenosome {ECO:0000255|PROSITE-ProRule:PRU10028, ECO:0000256|PIRNR:PIRNR001361}";
        assertEquals(expected, organelle.getDisplayed(""));
        TestHelper.verifyJson(organelle);
    }

    @Test
    public void testGetDisplayedValuePLASTID() {
        GeneEncodingType type = GeneEncodingType.PLASTID;
        String val = "some value";
        List<Evidence> evidences = createEvidences();
        Organelle organelle = new OrganelleImpl(type, val, evidences);
        String expected = "Plastid {ECO:0000255|PROSITE-ProRule:PRU10028, ECO:0000256|PIRNR:PIRNR001361}";
        assertEquals(expected, organelle.getDisplayed(""));
        TestHelper.verifyJson(organelle);
    }

    @Test
    public void testGetDisplayedValuePLASMID() {
        GeneEncodingType type = GeneEncodingType.PLASMID;
        String val = "some value";
        List<Evidence> evidences = createEvidences();
        Organelle organelle = new OrganelleImpl(type, val, evidences);
        String expected = "Plasmid some value {ECO:0000255|PROSITE-ProRule:PRU10028, ECO:0000256|PIRNR:PIRNR001361}";
        assertEquals(expected, organelle.getDisplayed(""));
        TestHelper.verifyJson(organelle);
    }

    @Test
    public void testOrganelleImpl() {
        GeneEncodingType type = GeneEncodingType.APICOPLAST_PLASTID;
        String val = "";
        List<Evidence> evidences = null;
        Organelle organelle = new OrganelleImpl(type, val, evidences);
        assertEquals(type, organelle.getGeneEncodingType());
        assertEquals(val, organelle.getValue());
        assertTrue(organelle.getEvidences().isEmpty());
        TestHelper.verifyJson(organelle);
    }
}
