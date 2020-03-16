package org.uniprot.core.uniprotkb.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.gene.ORFName;
import org.uniprot.core.uniprotkb.evidence.impl.AbstractEvidencedValueBuilderTest;

class ORFNameBuilderTest extends AbstractEvidencedValueBuilderTest {

    @Test
    void checkORFNameBuilderCreationIsAsExpected() {
        ORFName orfName =
                new ORFNameBuilder((getValue()), getEvidenceList())
                        .evidencesAdd(getEvidence())
                        .build();

        verifyEvidencedValue(orfName);
    }
}
