package uk.ac.ebi.uniprot.domain.uniprot.evidence;

import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceImpl;

import org.junit.Test;

import com.google.common.base.Strings;

import static org.junit.Assert.*;

public class EvidenceImplTest {

    @Test
    public void testFrom() {
        String val ="ECO:0000269|PubMed:22481068";
        Evidence evidence = EvidenceImpl.parseEvidenceLine(val);
        assertEquals(val, evidence.getValue());    
         verify(evidence, val, EvidenceCode.ECO_0000269, "PubMed", "22481068");
    }
    void verify(Evidence evidence, String expected, EvidenceCode type,  String dbName, String id) {
    	 assertEquals(expected, evidence.getValue());    
    	 assertEquals(type, evidence.getEvidenceCode());
    	 if(Strings.isNullOrEmpty(dbName)) {
    		 assertNull(evidence.getSource());
    	 }else {
    		 assertNotNull(evidence.getSource());
    	 assertEquals(dbName, evidence.getSource().getDatabaseName());
    	 assertEquals(id, evidence.getSource().getId());
    	 }
    	 TestHelper.verifyJson(evidence);
    }
    @Test
    public void testFromNoAttr() {
        String val ="ECO:0000305";
        Evidence evidence = EvidenceImpl.parseEvidenceLine(val);
        verify(evidence, val, EvidenceCode.ECO_0000305, "", "");
    }

    
    @Test
    public void testFromProteome() {
        String val ="ECO:0000313|Proteomes:UP000068497";
        Evidence evidence = EvidenceImpl.parseEvidenceLine(val);
        verify(evidence, val, EvidenceCode.ECO_0000313, "Proteomes", "UP000068497");
    }
    
    @Test
    public void testFromEmbl() {
        String val ="ECO:0000313|EMBL:AKQ52526.1";
        Evidence evidence = EvidenceImpl.parseEvidenceLine(val);
        verify(evidence, val, EvidenceCode.ECO_0000313, "EMBL", "AKQ52526.1");
    }
    
    @Test
    public void testFromPirnr() {
        String val ="ECO:0000256|PIRNR:PIRNR001361";
        Evidence evidence = EvidenceImpl.parseEvidenceLine(val);
        verify(evidence, val, EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361");
    }
    
    @Test
    public void testFromPrositeRule() {
        String val ="ECO:0000255|PROSITE-ProRule:PRU10028";
        Evidence evidence = EvidenceImpl.parseEvidenceLine(val);
        verify(evidence, val, EvidenceCode.ECO_0000255, "PROSITE-ProRule", "PRU10028");
    }
  
    @Test
    public void testOpinion() {
    
    	 String val =	"ECO:0000303|Ref.6";
         Evidence evidence = EvidenceImpl.parseEvidenceLine(val);
         verify(evidence, val, EvidenceCode.ECO_0000303, "Reference", "Ref.6");
    }
    @Test
    public void testConstructor(){
        EvidenceImpl evidence = new EvidenceImpl(
                EvidenceCode.ECO_0000313, "Ensembl", "ENSP0001324"
                );
        
        verify(evidence, "ECO:0000313|Ensembl:ENSP0001324", EvidenceCode.ECO_0000313, "Ensembl", "ENSP0001324");
    }
    @Test
    public void testCompareTo() {
        EvidenceImpl evidence = new EvidenceImpl(EvidenceCode.ECO_0000313, "Ensembl", "ENSP0001324"
                );
        EvidenceImpl evidence2 = new EvidenceImpl(
                EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"
                );
        int val = evidence.compareTo(evidence2);
        assertTrue(val>0);
    }
}
