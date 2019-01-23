package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.impl.EvidenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.impl.EvidencedValueImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FreeTextImplTest {

    @Test
    void testFreeTextImplEmpty() {
        List<EvidencedValue> texts = new ArrayList<>();
        FreeTextImpl freeText = new FreeTextImpl(texts);
        assertEquals(texts, freeText.getTexts());
        TestHelper.verifyJson(freeText);
    }

    @Test
    void testFreeTextImpl() {
        List<EvidencedValue> texts = new ArrayList<>();
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(new EvidenceImpl(
                EvidenceCode.ECO_0000313, "Ensembl", "ENSP0001324"
        ));
        evidences.add(new EvidenceImpl(
                EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"
        ));
        texts.add(new EvidencedValueImpl("value 1", evidences));
        texts.add(new EvidencedValueImpl("value2", Collections.emptyList()));
        FreeTextImpl freeText = new FreeTextImpl(texts);
        assertEquals(texts, freeText.getTexts());
        TestHelper.verifyJson(freeText);
    }

}
