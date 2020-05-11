package org.uniprot.core.uniprotkb.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.gene.GeneName;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.impl.AbstractEvidencedValueBuilderTest;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilderTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GeneNameBuilderTest extends AbstractEvidencedValueBuilderTest {
    @Test
    void checkGeneNameSynonymBuilderCreationIsAsExpected() {
        GeneName orfName =
                new GeneNameBuilder(getValue(), getEvidenceList())
                        .evidencesAdd(getEvidence())
                        .build();

        verifyEvidencedValue(orfName);
    }

    public static GeneName createObject(int listSize, boolean includeEvidences) {
        GeneNameBuilder builder = new GeneNameBuilder();
        String random = UUID.randomUUID().toString();
        String name = "value-" + random;
        List<Evidence> evidences =
                includeEvidences ? EvidenceBuilderTest.createObjects(listSize) : new ArrayList<>();
        builder.value(name).evidencesSet(evidences);
        return builder.build();
    }

    public static GeneName createObject(int listSize) {
        return createObject(listSize, false);
    }

    public static GeneName createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<GeneName> createObjects(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count))
                .collect(Collectors.toList());
    }
}
