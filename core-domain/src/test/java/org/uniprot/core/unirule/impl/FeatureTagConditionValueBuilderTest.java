package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.FeatureTagConditionValue;

public class FeatureTagConditionValueBuilderTest {

    @Test
    void createTestSkinnyObject() {
        FeatureTagConditionValueBuilder builder = new FeatureTagConditionValueBuilder("value");
        FeatureTagConditionValue ftc = builder.build();
        assertNotNull(ftc);
        assertEquals("value", ftc.getValue());
        assertNull(ftc.getPattern());
    }

    @Test
    void createTestUpdatePattern() {
        FeatureTagConditionValue obj = createObject();
        FeatureTagConditionValueBuilder builder = FeatureTagConditionValueBuilder.from(obj);
        assertNotNull(builder);
        builder.pattern("new pattern");
        FeatureTagConditionValue updatedObj = builder.build();
        assertEquals(obj.getValue(), updatedObj.getValue());
        assertNotEquals(obj.getPattern(), updatedObj.getPattern());
    }

    public static FeatureTagConditionValue createObject(int listSize) {
        String random = UUID.randomUUID().toString();
        String value = "value-" + random;
        String pattern = "pattern-" + random;
        FeatureTagConditionValueBuilder builder = new FeatureTagConditionValueBuilder(value);
        builder.pattern(pattern);
        FeatureTagConditionValue tag = builder.build();
        assertNotNull(tag);
        assertEquals(value, tag.getValue());
        assertEquals(pattern, tag.getPattern());
        return tag;
    }

    public static FeatureTagConditionValue createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<FeatureTagConditionValue> createObjects(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count))
                .collect(Collectors.toList());
    }
}
