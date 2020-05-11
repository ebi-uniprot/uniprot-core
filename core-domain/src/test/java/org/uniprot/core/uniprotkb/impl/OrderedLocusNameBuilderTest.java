package org.uniprot.core.uniprotkb.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.gene.OrderedLocusName;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.impl.AbstractEvidencedValueBuilderTest;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilderTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OrderedLocusNameBuilderTest extends AbstractEvidencedValueBuilderTest {

    @Test
    void checkOrderedLocusNameBuilderCreationIsAsExpected() {
        OrderedLocusName orfName =
                new OrderedLocusNameBuilder((getValue()), getEvidenceList())
                        .evidencesAdd(getEvidence())
                        .build();

        verifyEvidencedValue(orfName);
    }

    public static OrderedLocusName createObject(int listSize, boolean includeEvidences) {
        OrderedLocusNameBuilder builder = new OrderedLocusNameBuilder();
        String random = UUID.randomUUID().toString();
        String value = "value-" + random;
        List<Evidence> evidences =
                includeEvidences ? EvidenceBuilderTest.createObjects(listSize) : new ArrayList<>();
        builder.value(value).evidencesSet(evidences);
        return builder.build();
    }

    public static OrderedLocusName createObject(int listSize) {
        return createObject(listSize, false);
    }

    public static OrderedLocusName createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<OrderedLocusName> createObjects(int count) {
        return createObjects(count, false);
    }

    public static List<OrderedLocusName> createObjects(int count, boolean includeEvidences) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count, includeEvidences))
                .collect(Collectors.toList());
    }
}
