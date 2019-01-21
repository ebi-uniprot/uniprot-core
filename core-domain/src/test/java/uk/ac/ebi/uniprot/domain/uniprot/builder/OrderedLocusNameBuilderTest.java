package uk.ac.ebi.uniprot.domain.uniprot.builder;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.gene.OrderedLocusName;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.AbstractEvidencedValueBuilderTest;

class OrderedLocusNameBuilderTest extends AbstractEvidencedValueBuilderTest {

    @Test
    void checkOrderedLocusNameBuilderCreationIsAsExpected() {
        OrderedLocusNameBuilder builder = new OrderedLocusNameBuilder();
        buildEvidencedValueParameters(builder);
        OrderedLocusName orfName = builder.build();

        verifyEvidencedValue(orfName);
    }

}