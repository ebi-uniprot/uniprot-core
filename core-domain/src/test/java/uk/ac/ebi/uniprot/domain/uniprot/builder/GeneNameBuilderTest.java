package uk.ac.ebi.uniprot.domain.uniprot.builder;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.gene.GeneName;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.builder.AbstractEvidencedValueBuilderTest;

class GeneNameBuilderTest extends AbstractEvidencedValueBuilderTest {
    @Test
    void checkGeneNameSynonymBuilderCreationIsAsExpected() {
        GeneName orfName = new GeneNameBuilder(getValue(), getEvidenceList()).addEvidence(getEvidence()).build();

        verifyEvidencedValue(orfName);
    }
}