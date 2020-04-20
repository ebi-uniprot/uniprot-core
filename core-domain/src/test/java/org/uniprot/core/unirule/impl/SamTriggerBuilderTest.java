package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.uniprot.core.Range;
import org.uniprot.core.unirule.SamTrigger;
import org.uniprot.core.unirule.SamTriggerType;

public class SamTriggerBuilderTest {
    public static SamTrigger createObject() {
        int rIndex = ThreadLocalRandom.current().nextInt(0, SamTriggerType.values().length);
        int start = ThreadLocalRandom.current().nextInt();
        int end = ThreadLocalRandom.current().nextInt(start + 1, Integer.MAX_VALUE);
        Range expectedHits = new Range(start, end);
        SamTriggerType samTriggerType = SamTriggerType.values()[rIndex];
        SamTriggerBuilder builder = new SamTriggerBuilder();
        builder.expectedHits(expectedHits).samTriggerType(samTriggerType);
        SamTrigger samTrigger = builder.build();
        assertNotNull(samTrigger);
        assertEquals(expectedHits, samTrigger.getExpectedHits());
        assertEquals(samTriggerType, samTrigger.getSamTriggerType());
        return samTrigger;
    }

    public static List<SamTrigger> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
