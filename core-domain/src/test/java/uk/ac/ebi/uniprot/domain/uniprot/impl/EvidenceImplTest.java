package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.uniprot.evidences.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.EvidenceType;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceImpl;

import org.junit.Test;

import static org.junit.Assert.*;

public class EvidenceImplTest {

    @Test
    public void testConstructor(){
        EvidenceImpl evidence = new EvidenceImpl(EvidenceType.Ensembl,
                EvidenceCode.ECO_0000313, "ENSP0001324"
                );
        assertEquals(EvidenceType.Ensembl, evidence.getType());
        assertEquals(EvidenceCode.ECO_0000313, evidence.getEvidenceCode());
        assertEquals("ENSP0001324", evidence.getAttribute());
        assertEquals("ECO:0000313|Ensembl:ENSP0001324", evidence.getValue());
    }
    @Test
    public void testCompareTo() {
        EvidenceImpl evidence = new EvidenceImpl(EvidenceType.Ensembl,
                EvidenceCode.ECO_0000313, "ENSP0001324"
                );
        EvidenceImpl evidence2 = new EvidenceImpl(EvidenceType.PIRNR,
                EvidenceCode.ECO_0000256, "PIRNR001361"
                );
        int val = evidence.compareTo(evidence2);
        assertTrue(val>0);
    }

}
