package uk.ac.ebi.uniprot.domain.uniprot.builder;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.gene.GeneNameSynonym;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.AbstractEvidencedValueBuilderTest;

class GeneNameSynonymBuilderTest extends AbstractEvidencedValueBuilderTest {

    @Test
    void checkGeneNameSynonymBuilderCreationIsAsExpected() {
        GeneNameSynonym orfName = new GeneNameSynonymBuilder(getValue(), getEvidenceList())
                .addEvidence(getEvidence()).build();

        verifyEvidencedValue(orfName);
    }

}