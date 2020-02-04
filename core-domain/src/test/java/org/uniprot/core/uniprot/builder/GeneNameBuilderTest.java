package org.uniprot.core.uniprot.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.gene.GeneName;
import org.uniprot.core.uniprot.evidence.builder.AbstractEvidencedValueBuilderTest;

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
