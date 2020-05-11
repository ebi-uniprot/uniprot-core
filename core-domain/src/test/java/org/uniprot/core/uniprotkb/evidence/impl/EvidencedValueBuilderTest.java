package org.uniprot.core.uniprotkb.evidence.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EvidencedValueBuilderTest {
    public static EvidencedValue createObject(int listSize, boolean includeEvidence) {
        String random = UUID.randomUUID().toString();
        String value = "value-" + random;
        List<Evidence> evidences =
                includeEvidence ? EvidenceBuilderTest.createObjects(listSize) : new ArrayList<>();
        EvidencedValueBuilder builder = new EvidencedValueBuilder();
        builder.value(value);
        builder.evidencesSet(evidences);
        EvidencedValue evidenceValue = builder.build();
        assertNotNull(evidenceValue);
        assertEquals(value, evidenceValue.getValue());
        assertEquals(evidences, evidenceValue.getEvidences());
        return evidenceValue;
    }

    public static EvidencedValue createObject(int listSize) {
        return createObject(listSize, false);
    }

    public static EvidencedValue createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<EvidencedValue> createObjects(int count) {
        return createObjects(count, false);
    }

    public static List<EvidencedValue> createObjects(int count, boolean includeEvidence) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count, includeEvidence))
                .collect(Collectors.toList());
    }
}
