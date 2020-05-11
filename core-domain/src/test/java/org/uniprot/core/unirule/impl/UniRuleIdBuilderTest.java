package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.uniprot.core.unirule.UniRuleId;

public class UniRuleIdBuilderTest {

    public static UniRuleId createObject(int listSize) {
        String random = UUID.randomUUID().toString();
        String id = "id-" + random;
        UniRuleId uniRuleId = new UniRuleIdBuilder(id).build();
        assertNotNull(uniRuleId);
        assertEquals(id, uniRuleId.getValue());
        return uniRuleId;
    }

    public static UniRuleId createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<UniRuleId> createObjects(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count))
                .collect(Collectors.toList());
    }
}
