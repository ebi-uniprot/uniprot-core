package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.EvidenceType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

public class EvidencedValueImplTest {

    @Test
    public void testEmptyEvidence() {
        String value = "some value";
        List<Evidence> evidences =Collections.emptyList();
        EvidencedValueImpl evidencedValue =new EvidencedValueImpl(value, evidences); 
        assertEquals(value, evidencedValue.getValue());
        assertEquals(evidences, evidencedValue.getEvidences());
    }
    @Test
    public void testTwoEvidences() {
        String value = "some values";
        List<Evidence> evidences =new ArrayList<>();
        evidences.add(new EvidenceImpl(EvidenceType.Ensembl,
                EvidenceCode.ECO_0000313, "ENSP0001324"
                ));
        evidences.add(new EvidenceImpl(EvidenceType.PIRNR,
                EvidenceCode.ECO_0000256, "PIRNR001361"
                ));
        EvidencedValueImpl evidencedValue =new EvidencedValueImpl(value, evidences); 
        assertEquals(value, evidencedValue.getValue());
        assertEquals(evidences, evidencedValue.getEvidences());
    }
}
