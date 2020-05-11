package org.uniprot.core.uniprotkb.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.uniprotkb.Keyword;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidenceCode;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class KeywordImplTest {

    @Test
    void testKeywordImpl() {
        String val = "Transmembrane";
        String id = "KW-0812";
        KeywordCategory category = KeywordCategory.DOMAIN;
        List<Evidence> evidences = createEvidences();
        Keyword keyword = new KeywordImpl(id, val, category, evidences);
        assertEquals(val, keyword.getName());
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
        assertEquals(category, keyword.getCategory());
    }

    @Test
    void idWillNeverBeNull() {
        Keyword obj = new KeywordImpl();
        assertNotNull(obj.getId());
        assertFalse(obj.getId().isEmpty());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Keyword obj = new KeywordImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        Keyword impl =
                new KeywordImpl("abc", "edf", KeywordCategory.UNKNOWN, Collections.emptyList());
        Keyword obj = KeywordBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(
                new EvidenceBuilder()
                        .evidenceCode(EvidenceCode.ECO_0000313)
                        .databaseName("Ensembl")
                        .databaseId("ENSP0001324")
                        .build());
        evidences.add(
                new EvidenceBuilder()
                        .evidenceCode(EvidenceCode.ECO_0000256)
                        .databaseName("PIRNR")
                        .databaseId("PIRNR001361")
                        .build());
        return evidences;
    }
}
