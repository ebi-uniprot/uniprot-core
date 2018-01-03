package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.EvidenceType;
import uk.ac.ebi.uniprot.domain.uniprot.factory.EvidenceFactory;

import org.junit.Test;

import static org.junit.Assert.*;

public class EvidenceFactoryTest {

    @Test
    public void testFrom() {
        String val ="ECO:0000269|PubMed:22481068";
        Evidence evidence = EvidenceFactory.INSTANCE.createFromEvidenceLine(val);
        assertEquals(val, evidence.getValue());    
    }
    
    @Test
    public void testFromNoAttr() {
        String val ="ECO:0000305";
        Evidence evidence = EvidenceFactory.INSTANCE.createFromEvidenceLine(val);
        assertEquals(val, evidence.getValue());    
    }

    
    @Test
    public void testFromProteome() {
        String val ="ECO:0000313|Proteomes:UP000068497";
        Evidence evidence = EvidenceFactory.INSTANCE.createFromEvidenceLine(val);
        assertEquals(val, evidence.getValue());    
    }
    
    @Test
    public void testFromEmbl() {
        String val ="ECO:0000313|EMBL:AKQ52526.1";
        Evidence evidence = EvidenceFactory.INSTANCE.createFromEvidenceLine(val);
        assertEquals(val, evidence.getValue());    
    }
    
    @Test
    public void testFromPirnr() {
        String val ="ECO:0000256|PIRNR:PIRNR001361";
        Evidence evidence = EvidenceFactory.INSTANCE.createFromEvidenceLine(val);
        assertEquals(val, evidence.getValue());    
    }
    
    @Test
    public void testFromPrositeRule() {
        String val ="ECO:0000255|PROSITE-ProRule:PRU10028";
        Evidence evidence = EvidenceFactory.INSTANCE.createFromEvidenceLine(val);
        assertEquals(val, evidence.getValue());    
    }
  
    @Test
    public void testConvertToEvidenceType() {
      EvidenceType type=  EvidenceFactory.INSTANCE.convertToEvidenceType(EvidenceCode.ECO_0000269);    
      assertEquals(EvidenceType.EXPERIMENTAL, type);
      
      type=  EvidenceFactory.INSTANCE.convertToEvidenceType(EvidenceCode.ECO_0000255);    
      assertEquals(EvidenceType.HAMAP_RULE, type);
    }

}
