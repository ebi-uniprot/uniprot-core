package org.uniprot.core.unirule.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.uniprot.core.unirule.UniRuleId;

public class UniRuleIdBuilderTest {

    public static UniRuleId createObject() {
        String random = UUID.randomUUID().toString();
        String id = "id-" + random;
        UniRuleId uniRuleId = new UniRuleIdBuilder(id).build();
        assertNotNull(uniRuleId);
        assertEquals(id, uniRuleId.getValue());
        return uniRuleId;
    }

    public static List<UniRuleId> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
