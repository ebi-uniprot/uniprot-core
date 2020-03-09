package org.uniprot.core.uniprot.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.gene.GeneNameSynonym;
import org.uniprot.core.uniprot.evidence.impl.AbstractEvidencedValueBuilderTest;

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
