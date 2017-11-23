package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.uniprot.Keyword;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.EvidenceType;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

public class KeywordImplTest {

    @Test
    public void testKeywordImpl() {
        String val = "Transmembrane";
        List<Evidence> evidences = createEvidences();
        Keyword keyword = new KeywordImpl(val, evidences);
        assertEquals(val, keyword.getValue());
        assertEquals(2, keyword.getEvidences().size());
    }
    @Test
    public void testGetDisplayedValue(){
        String val = "Transmembrane";
        List<Evidence> evidences = createEvidences();
        Keyword keyword = new KeywordImpl(val, evidences);
        String expected = "Transmembrane {ECO:0000313|Ensembl:ENSP0001324, ECO:0000256|PIRNR:PIRNR001361}";
        assertEquals(expected, keyword.getDisplayedValue(""));
    }
    
    private List<Evidence> createEvidences(){
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(new EvidenceImpl(EvidenceType.Ensembl,
                EvidenceCode.ECO_0000313, "ENSP0001324"));
        evidences.add(new EvidenceImpl(EvidenceType.PIRNR,
                EvidenceCode.ECO_0000256, "PIRNR001361"));
        
        return evidences;
    }
}
