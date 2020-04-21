package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.uniprot.core.Range;
import org.uniprot.core.unirule.PositionalFeature;

public class PositionalFeatureBuilderTest {
    public static PositionalFeature createObject() {
        String random = UUID.randomUUID().toString();
        String pattern = "pattern-" + random;
        String type = "type-" + random;
        String value = "value-" + random;
        int start = ThreadLocalRandom.current().nextInt();
        int end = ThreadLocalRandom.current().nextInt(start + 1, Integer.MAX_VALUE);
        Range position = new Range(start, end);
        boolean inGroup = true;
        PositionalFeatureBuilder builder = new PositionalFeatureBuilder();
        builder.type(type).pattern(pattern).value(value);
        builder.position(position).inGroup(inGroup);
        PositionalFeature positionFeature = builder.build();
        assertNotNull(positionFeature);
        assertEquals(type, positionFeature.getType());
        assertEquals(pattern, positionFeature.getPattern());
        assertEquals(value, positionFeature.getValue());
        assertEquals(position, positionFeature.getPosition());
        assertEquals(inGroup, positionFeature.isInGroup());
        return positionFeature;
    }

    public static List<PositionalFeature> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
