package uk.ac.ebi.uniprot.domain.uniprot.builder;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.gene.GeneName;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.AbstractEvidencedValueBuilderTest;

class GeneNameBuilderTest extends AbstractEvidencedValueBuilderTest {

    @Test
    void checkGeneNameSynonymBuilderCreationIsAsExpected() {
        GeneNameBuilder builder = new GeneNameBuilder();
        buildEvidencedValueParameters(builder);
        GeneName orfName = builder.build();

        verifyEvidencedValue(orfName);
    }

}