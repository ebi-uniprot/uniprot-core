package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceCode;
import org.uniprot.core.uniprot.evidence.EvidencedValue;
import org.uniprot.core.uniprot.evidence.impl.EvidenceImpl;
import org.uniprot.core.uniprot.evidence.impl.EvidencedValueImpl;

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
        evidences.add(new EvidenceImpl(EvidenceCode.ECO_0000313, "Ensembl", "ENSP0001324"));
        evidences.add(new EvidenceImpl(EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"));
        texts.add(new EvidencedValueImpl("value 1", evidences));
        texts.add(new EvidencedValueImpl("value2", Collections.emptyList()));
        FreeTextImpl freeText = new FreeTextImpl(texts);
        assertEquals(texts, freeText.getTexts());
    }
}
