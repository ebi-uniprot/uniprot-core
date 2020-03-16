package org.uniprot.core.uniprotkb.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.gene.GeneNameSynonym;
import org.uniprot.core.uniprotkb.evidence.impl.AbstractEvidencedValueBuilderTest;

class GeneNameSynonymBuilderTest extends AbstractEvidencedValueBuilderTest {

    @Test
    void checkGeneNameSynonymBuilderCreationIsAsExpected() {
        GeneNameSynonym orfName =
                new GeneNameSynonymBuilder(getValue(), getEvidenceList())
                        .evidencesAdd(getEvidence())
                        .build();

        verifyEvidencedValue(orfName);
    }
}
