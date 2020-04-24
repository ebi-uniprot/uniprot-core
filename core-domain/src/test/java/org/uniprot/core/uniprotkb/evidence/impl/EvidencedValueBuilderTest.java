package org.uniprot.core.uniprotkb.evidence.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

public class EvidencedValueBuilderTest {
    public static EvidencedValue createObject(int listSize) {
        String random = UUID.randomUUID().toString();
        String value = "value-" + random;
        List<Evidence> evidences = EvidenceBuilderTest.createObjects(listSize);
        EvidencedValueBuilder builder = new EvidencedValueBuilder();
        builder.value(value);
        builder.evidencesSet(evidences);
        EvidencedValue evidenceValue = builder.build();
        assertNotNull(evidenceValue);
        assertEquals(value, evidenceValue.getValue());
        assertEquals(evidences, evidenceValue.getEvidences());
        return evidenceValue;
    }

    public static EvidencedValue createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<EvidencedValue> createObjects(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count))
                .collect(Collectors.toList());
    }
}
