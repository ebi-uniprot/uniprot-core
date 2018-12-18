package uk.ac.ebi.uniprot.domain.uniprot.impl;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.Keyword;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class KeywordImplTest {

    @Test
    public void testKeywordImpl() {
        String val = "Transmembrane";
        String id = "KW-0812";
        List<Evidence> evidences = createEvidences();
        Keyword keyword = new KeywordImpl(id, val, evidences);
        assertEquals(val, keyword.getValue());
        assertEquals(2, keyword.getEvidences().size());
        TestHelper.verifyJson(keyword);
    }

    @Test
    public void testGetDisplayedValue() {
        String val = "Transmembrane";
        String id = "KW-0812";
        List<Evidence> evidences = createEvidences();
        Keyword keyword = new KeywordImpl(id, val, evidences);
        String expected = "Transmembrane {ECO:0000313|Ensembl:ENSP0001324, ECO:0000256|PIRNR:PIRNR001361}";
        assertEquals(expected, keyword.getDisplayed(" "));
        TestHelper.verifyJson(keyword);
    }

    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(new EvidenceImpl(
                EvidenceCode.ECO_0000313, "Ensembl", "ENSP0001324"));
        evidences.add(new EvidenceImpl(
                EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"));

        return evidences;
    }
}
