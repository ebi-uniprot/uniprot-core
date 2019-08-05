package org.uniprot.core.uniprot.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.gene.GeneNameSynonym;
import org.uniprot.core.uniprot.builder.GeneNameSynonymBuilder;
import org.uniprot.core.uniprot.evidence.builder.AbstractEvidencedValueBuilderTest;

class GeneNameSynonymBuilderTest extends AbstractEvidencedValueBuilderTest {

    @Test
    void checkGeneNameSynonymBuilderCreationIsAsExpected() {
        GeneNameSynonym orfName = new GeneNameSynonymBuilder(getValue(), getEvidenceList())
                .addEvidence(getEvidence()).build();

        verifyEvidencedValue(orfName);
    }

}