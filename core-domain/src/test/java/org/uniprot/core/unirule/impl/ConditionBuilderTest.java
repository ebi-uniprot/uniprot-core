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
import org.uniprot.core.unirule.FtagConditionValue;

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
        assertNull(condition.getTag());
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
        assertNotNull(updatedCondition.getTag());
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

    @Test
    void testCreateObjectUpdateTag() {
        Condition condition = createObject();
        assertNotNull(condition.getTag());

        ConditionBuilder builder = ConditionBuilder.from(condition);
        builder.tag(null);
        Condition updatedCondition = builder.build();

        assertNotNull(updatedCondition);
        assertNull(updatedCondition.getTag());
        assertEquals(condition.getType(), updatedCondition.getType());
        assertEquals(condition.getRange(), updatedCondition.getRange());
        assertEquals(condition.isNegative(), updatedCondition.isNegative());
        assertEquals(condition.getConditionValues(), updatedCondition.getConditionValues());
    }

    public static Condition createObject(int listSize) {
        String random = UUID.randomUUID().toString();
        String type = "type-" + random;
        int start = ThreadLocalRandom.current().nextInt();
        int end = ThreadLocalRandom.current().nextInt(start + 1, Integer.MAX_VALUE);
        Range range = new Range(start, end);
        boolean negative = true;
        List<ConditionValue> conditionValues = ConditionValueBuilderTest.createObjects(listSize);
        FtagConditionValue tag = FtagConditionValueBuilderTest.createObject();
        ConditionBuilder builder = new ConditionBuilder(type);
        builder.type(type).range(range).negative(negative);
        builder.conditionValuesSet(conditionValues);
        builder.tag(tag);
        Condition condition = builder.build();
        assertNotNull(condition);
        assertEquals(type, condition.getType());
        assertEquals(range, condition.getRange());
        assertEquals(negative, condition.isNegative());
        assertEquals(conditionValues, condition.getConditionValues());
        assertEquals(tag, condition.getTag());
        return condition;
    }

    public static Condition createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<Condition> createObjects(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count))
                .collect(Collectors.toList());
    }
}
