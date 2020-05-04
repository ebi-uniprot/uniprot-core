package org.uniprot.core.uniprotkb.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.uniprot.core.gene.ORFName;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.impl.AbstractEvidencedValueBuilderTest;

class ORFNameBuilderTest extends AbstractEvidencedValueBuilderTest {

    @Test
    void checkORFNameBuilderCreationIsAsExpected() {
        ORFName orfName =
                new ORFNameBuilder((getValue()), getEvidenceList())
                        .evidencesAdd(getEvidence())
                        .build();

        verifyEvidencedValue(orfName);
    }

    public static ORFName createObject(int listSize) {
        ORFNameBuilder builder = new ORFNameBuilder();
        String random = UUID.randomUUID().toString();
        String value = "value-" + random;
        List<Evidence> evidences = new ArrayList<>();
        builder.value(value).evidencesSet(evidences);
        return builder.build();
    }

    public static ORFName createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<ORFName> createObjects(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count))
                .collect(Collectors.toList());
    }
}
