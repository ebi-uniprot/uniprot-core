package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.ConditionValue;

public class ConditionValueBuilderTest {

    @Test
    void testCreateSkinnyObject() {
        String value = "sample value";
        ConditionValue conditionValue = new ConditionValueBuilder(value).build();
        assertNotNull(conditionValue);
        assertEquals(value, conditionValue.getValue());
        assertNull(conditionValue.getCvId());
    }

    public static ConditionValue createObject() {
        String random = UUID.randomUUID().toString();
        String cvId = "cvId" + random;
        String value = "value" + random;
        ConditionValueBuilder builder = new ConditionValueBuilder(value);
        builder.cvId(cvId);
        ConditionValue conditionValue = builder.build();
        assertNotNull(conditionValue);
        assertEquals(value, conditionValue.getValue());
        assertEquals(cvId, conditionValue.getCvId());
        return conditionValue;
    }

    public static List<ConditionValue> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
