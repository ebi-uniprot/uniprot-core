package org.uniprot.core.uniprot.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.gene.ORFName;
import org.uniprot.core.uniprot.builder.ORFNameBuilder;
import org.uniprot.core.uniprot.evidence.builder.AbstractEvidencedValueBuilderTest;

class ORFNameBuilderTest extends AbstractEvidencedValueBuilderTest {

    @Test
    void checkORFNameBuilderCreationIsAsExpected() {
        ORFName orfName = new ORFNameBuilder((getValue()), getEvidenceList()).addEvidence(getEvidence()).build();

        verifyEvidencedValue(orfName);
    }

}