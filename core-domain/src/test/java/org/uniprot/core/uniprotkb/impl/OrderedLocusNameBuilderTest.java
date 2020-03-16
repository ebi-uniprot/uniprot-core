package org.uniprot.core.uniprotkb.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.gene.OrderedLocusName;
import org.uniprot.core.uniprotkb.evidence.impl.AbstractEvidencedValueBuilderTest;

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
