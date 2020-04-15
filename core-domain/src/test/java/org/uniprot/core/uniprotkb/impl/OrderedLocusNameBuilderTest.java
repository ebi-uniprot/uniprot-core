package org.uniprot.core.uniprotkb.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.uniprot.core.gene.OrderedLocusName;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.impl.AbstractEvidencedValueBuilderTest;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilderTest;

public class OrderedLocusNameBuilderTest extends AbstractEvidencedValueBuilderTest {

    @Test
    void checkOrderedLocusNameBuilderCreationIsAsExpected() {
        OrderedLocusName orfName =
                new OrderedLocusNameBuilder((getValue()), getEvidenceList())
                        .evidencesAdd(getEvidence())
                        .build();

        verifyEvidencedValue(orfName);
    }

    public static OrderedLocusName createObject() {
        OrderedLocusNameBuilder builder = new OrderedLocusNameBuilder();
        String random = UUID.randomUUID().toString();
        String value = "value-" + random;
        List<Evidence> evidences = EvidenceBuilderTest.createObjects(4);
        builder.value(value).evidencesSet(evidences);
        return builder.build();
    }

    public static List<OrderedLocusName> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
