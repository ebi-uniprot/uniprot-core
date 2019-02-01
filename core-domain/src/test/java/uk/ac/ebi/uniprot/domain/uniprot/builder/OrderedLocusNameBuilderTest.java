package uk.ac.ebi.uniprot.domain.uniprot.builder;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.gene.OrderedLocusName;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.builder.AbstractEvidencedValueBuilderTest;

class OrderedLocusNameBuilderTest extends AbstractEvidencedValueBuilderTest {

    @Test
    void checkOrderedLocusNameBuilderCreationIsAsExpected() {
        OrderedLocusName orfName = new OrderedLocusNameBuilder((getValue()), getEvidenceList())
                .addEvidence(getEvidence()).build();

        verifyEvidencedValue(orfName);
    }

}