package org.uniprot.core.uniprot.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.gene.OrderedLocusName;
import org.uniprot.core.uniprot.evidence.impl.AbstractEvidencedValueBuilderTest;

class OrderedLocusNameBuilderTest extends AbstractEvidencedValueBuilderTest {

    @Test
    void checkOrderedLocusNameBuilderCreationIsAsExpected() {
        OrderedLocusName orfName =
                new OrderedLocusNameBuilder((getValue()), getEvidenceList())
                        .evidencesAdd(getEvidence())
                        .build();

        verifyEvidencedValue(orfName);
    }
}
