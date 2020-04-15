package org.uniprot.core.uniprotkb.impl;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.uniprot.core.gene.GeneName;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.impl.AbstractEvidencedValueBuilderTest;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilderTest;

public class GeneNameBuilderTest extends AbstractEvidencedValueBuilderTest {
    @Test
    void checkGeneNameSynonymBuilderCreationIsAsExpected() {
        GeneName orfName =
                new GeneNameBuilder(getValue(), getEvidenceList())
                        .evidencesAdd(getEvidence())
                        .build();

        verifyEvidencedValue(orfName);
    }

    public static GeneName createObject() {
        GeneNameBuilder builder = new GeneNameBuilder();
        String random = UUID.randomUUID().toString();
        String name = "value-" + random;
        List<Evidence> evidences = EvidenceBuilderTest.createObjects(4);
        builder.value(name).evidencesSet(evidences);
        return builder.build();
    }
}
