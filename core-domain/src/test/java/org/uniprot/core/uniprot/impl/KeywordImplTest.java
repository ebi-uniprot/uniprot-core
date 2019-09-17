package org.uniprot.core.uniprot.impl;


import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.uniprot.Keyword;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceCode;
import org.uniprot.core.uniprot.evidence.impl.EvidenceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KeywordImplTest {

    @Test
    void testKeywordImpl() {
        String val = "Transmembrane";
        String id = "KW-0812";
        KeywordCategory category = KeywordCategory.DOMAIN;
        List<Evidence> evidences = createEvidences();
        Keyword keyword = new KeywordImpl(id, val, category, evidences);
        assertEquals(val, keyword.getValue());
        assertEquals(2, keyword.getEvidences().size());
        assertEquals(category, keyword.getCategory());
    }

    @Test
    void testGetDisplayedValue() {
        String val = "Transmembrane";
        String id = "KW-0812";
        KeywordCategory category = KeywordCategory.DOMAIN;
        List<Evidence> evidences = createEvidences();
        Keyword keyword = new KeywordImpl(id, val, category, evidences);
        String expected = "Transmembrane {ECO:0000313|Ensembl:ENSP0001324, ECO:0000256|PIRNR:PIRNR001361}";
        assertEquals(expected, keyword.getDisplayed(" "));
        assertEquals(category, keyword.getCategory());
       
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
