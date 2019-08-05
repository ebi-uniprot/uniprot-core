package org.uniprot.core.uniprot.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.gene.OrderedLocusName;
import org.uniprot.core.uniprot.builder.OrderedLocusNameBuilder;
import org.uniprot.core.uniprot.evidence.builder.AbstractEvidencedValueBuilderTest;

class OrderedLocusNameBuilderTest extends AbstractEvidencedValueBuilderTest {

    @Test
    void checkOrderedLocusNameBuilderCreationIsAsExpected() {
        OrderedLocusName orfName = new OrderedLocusNameBuilder((getValue()), getEvidenceList())
                .addEvidence(getEvidence()).build();

        verifyEvidencedValue(orfName);
    }

}