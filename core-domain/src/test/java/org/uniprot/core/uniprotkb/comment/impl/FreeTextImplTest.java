package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.FreeText;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidenceCode;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilder;
import org.uniprot.core.uniprotkb.evidence.impl.EvidencedValueBuilder;

class FreeTextImplTest {

    @Test
    void testFreeTextImplEmpty() {
        List<EvidencedValue> texts = new ArrayList<>();
        FreeTextImpl freeText = new FreeTextImpl(texts);
        assertEquals(texts, freeText.getTexts());
    }

    @Test
    void testFreeTextImpl() {
        List<EvidencedValue> texts = new ArrayList<>();
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
        texts.add(new EvidencedValueBuilder().value("value 1").evidencesSet(evidences).build());
        texts.add(new EvidencedValueBuilder().value("value2").build());
        FreeTextImpl freeText = new FreeTextImpl(texts);
        assertEquals(texts, freeText.getTexts());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        FreeText obj = new FreeTextImpl();
        assertNotNull(obj);
    }
}
