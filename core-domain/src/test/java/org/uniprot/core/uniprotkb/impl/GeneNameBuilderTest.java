package org.uniprot.core.uniprotkb.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.gene.GeneName;
import org.uniprot.core.uniprotkb.evidence.impl.AbstractEvidencedValueBuilderTest;

class GeneNameBuilderTest extends AbstractEvidencedValueBuilderTest {
    @Test
    void checkGeneNameSynonymBuilderCreationIsAsExpected() {
        GeneName orfName =
                new GeneNameBuilder(getValue(), getEvidenceList())
                        .evidencesAdd(getEvidence())
                        .build();

        verifyEvidencedValue(orfName);
    }
}
