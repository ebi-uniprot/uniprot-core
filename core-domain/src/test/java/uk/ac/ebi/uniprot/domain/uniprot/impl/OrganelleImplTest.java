package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.GeneEncodingType;
import uk.ac.ebi.uniprot.domain.uniprot.Organelle;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrganelleImplTest {

    @Test
    public void testGetDisplayedValueAPICOPLAST_PLASTID() {
        GeneEncodingType type = GeneEncodingType.APICOPLAST_PLASTID;
        String val ="";
        List<Evidence > evidences =createEvidences();
        Organelle organelle = new OrganelleImpl(type, val, evidences);
        String expected = "Plastid; Apicoplast {ECO:0000313|Ensembl:ENSP0001324, ECO:0000256|PIRNR:PIRNR001361}";
        assertEquals(expected, organelle.getDisplayed(""));
        TestHelper.verifyJson(organelle);
    }
    
    @Test
    public void testGetDisplayedValueMITOCHONDRION() {
        GeneEncodingType type = GeneEncodingType.MITOCHONDRION;
        String val ="some value";
        List<Evidence > evidences =createEvidences();
        Organelle organelle = new OrganelleImpl(type, val, evidences);
        String expected = "Mitochondrion {ECO:0000313|Ensembl:ENSP0001324, ECO:0000256|PIRNR:PIRNR001361}";
        assertEquals(expected, organelle.getDisplayed(""));
        TestHelper.verifyJson(organelle);
    }

    @Test
    public void testGetDisplayedValueHYDROGENOSOME() {
        GeneEncodingType type = GeneEncodingType.HYDROGENOSOME;
        String val ="some value";
        List<Evidence > evidences =createEvidences();
        Organelle organelle = new OrganelleImpl(type, val, evidences);
        String expected = "Hydrogenosome {ECO:0000313|Ensembl:ENSP0001324, ECO:0000256|PIRNR:PIRNR001361}";
        assertEquals(expected, organelle.getDisplayed(""));
        TestHelper.verifyJson(organelle);
    }
    
    @Test
    public void testGetDisplayedValuePLASTID() {
        GeneEncodingType type = GeneEncodingType.PLASTID;
        String val ="some value";
        List<Evidence > evidences =createEvidences();
        Organelle organelle = new OrganelleImpl(type, val, evidences);
        String expected = "Plastid {ECO:0000313|Ensembl:ENSP0001324, ECO:0000256|PIRNR:PIRNR001361}";
        assertEquals(expected, organelle.getDisplayed(""));
        TestHelper.verifyJson(organelle);
    }
    
    @Test
    public void testGetDisplayedValuePLASMID() {
        GeneEncodingType type = GeneEncodingType.PLASMID;
        String val ="some value";
        List<Evidence > evidences =createEvidences();
        Organelle organelle = new OrganelleImpl(type, val, evidences);
        String expected = "Plasmid some value {ECO:0000313|Ensembl:ENSP0001324, ECO:0000256|PIRNR:PIRNR001361}";
        assertEquals(expected, organelle.getDisplayed(""));
        TestHelper.verifyJson(organelle);
    }

    @Test
    public void testOrganelleImpl() {
        GeneEncodingType type = GeneEncodingType.APICOPLAST_PLASTID;
        String val ="";
        List<Evidence > evidences =null;
        Organelle organelle = new OrganelleImpl(type, val, evidences);
        assertEquals(type, organelle.getGeneEncodingType());
        assertEquals(val, organelle.getValue());
        assertTrue(organelle.getEvidences().isEmpty());
        TestHelper.verifyJson(organelle);
    }
    private List<Evidence> createEvidences(){
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(new EvidenceImpl(
                EvidenceCode.ECO_0000313, "Ensembl", "ENSP0001324"));
        evidences.add(new EvidenceImpl(
                EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"));
        
        return evidences;
    }
}
