package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Range;
import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.ConditionValue;

public class ConditionBuilderTest {

    @Test
    void testCreateObjectWithNullMandatoryParamType() {
        ConditionBuilder builder = new ConditionBuilder(null);
        assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void testCreateSkinnyObject() {
        String type = "sample type";
        ConditionBuilder builder = new ConditionBuilder(type);
        Condition condition = builder.build();
        assertNotNull(condition);
        assertEquals(type, condition.getType());
        assertNull(condition.getRange());
        assertTrue(condition.getConditionValues().isEmpty());
        assertFalse(condition.isNegative());
    }

    @Test
    void testCreateObjectUpdateList() {
        Condition condition = createObject();

        ConditionBuilder builder = ConditionBuilder.from(condition);
        ConditionValue conditionValue = ConditionValueBuilderTest.createObject();
        // add another condition value
        builder.conditionValuesAdd(conditionValue);
        Condition updatedCondition = builder.build();

        assertNotNull(updatedCondition);
        assertEquals(condition.getType(), updatedCondition.getType());
        assertEquals(condition.getRange(), updatedCondition.getRange());
        assertEquals(condition.isNegative(), updatedCondition.isNegative());
        assertEquals(
                condition.getConditionValues().size() + 1,
                updatedCondition.getConditionValues().size());
    }

    @Test
    void testCreateSkinnyObjectWithOneItemInList() {
        String type = "sample type";
        ConditionValue conditionValue = ConditionValueBuilderTest.createObject();
        ConditionBuilder builder = new ConditionBuilder(type);
        builder.conditionValuesAdd(conditionValue);
        Condition condition = builder.build();
        assertNotNull(condition);
        assertEquals(type, condition.getType());
        assertEquals(Arrays.asList(conditionValue), condition.getConditionValues());
    }

    public static Condition createObject() {
        String random = UUID.randomUUID().toString();
        String type = "type-" + random;
        int start = ThreadLocalRandom.current().nextInt();
        int end = ThreadLocalRandom.current().nextInt(start + 1, Integer.MAX_VALUE);
        Range range = new Range(start, end);
        boolean negative = start % 2 == 0;
        List<ConditionValue> conditionValues = ConditionValueBuilderTest.createObjects(5);
        ConditionBuilder builder = new ConditionBuilder(type);
        builder.type(type).range(range).negative(negative);
        builder.conditionValuesSet(conditionValues);
        Condition condition = builder.build();
        assertNotNull(condition);
        assertEquals(type, condition.getType());
        assertEquals(range, condition.getRange());
        assertEquals(negative, condition.isNegative());
        assertEquals(conditionValues, condition.getConditionValues());
        return condition;
    }

    public static List<Condition> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
