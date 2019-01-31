package uk.ac.ebi.uniprot.domain.uniprot.builder;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.gene.ORFName;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.builder.AbstractEvidencedValueBuilderTest;

class ORFNameBuilderTest extends AbstractEvidencedValueBuilderTest {

    @Test
    void checkORFNameBuilderCreationIsAsExpected() {
        ORFName orfName = new ORFNameBuilder((getValue()), getEvidenceList()).addEvidence(getEvidence()).build();

        verifyEvidencedValue(orfName);
    }

}