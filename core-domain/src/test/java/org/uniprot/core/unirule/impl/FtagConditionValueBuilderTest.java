package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.uniprot.core.unirule.FtagConditionValue;

public class FtagConditionValueBuilderTest {

    public static FtagConditionValue createObject(int listSize) {
        String random = UUID.randomUUID().toString();
        String value = "value-" + random;
        String pattern = "pattern-" + random;
        FtagConditionValueBuilder builder = new FtagConditionValueBuilder(value);
        builder.pattern(pattern);
        FtagConditionValue tag = builder.build();
        assertNotNull(tag);
        assertEquals(value, tag.getValue());
        assertEquals(pattern, tag.getPattern());
        return tag;
    }

    public static FtagConditionValue createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<FtagConditionValue> createObjects(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count))
                .collect(Collectors.toList());
    }
}
