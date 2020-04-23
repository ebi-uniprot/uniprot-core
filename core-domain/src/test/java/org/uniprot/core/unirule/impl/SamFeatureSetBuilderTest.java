package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.SamFeatureSet;
import org.uniprot.core.unirule.SamTrigger;

public class SamFeatureSetBuilderTest {
    @Test
    void testCreateObjectUpdateConditionsList() {
        SamFeatureSet samFeatureSet = createObject();
        // add a new condition
        SamFeatureSetBuilder builder = SamFeatureSetBuilder.from(samFeatureSet);
        builder.conditionsAdd(ConditionBuilderTest.createObject());
        SamFeatureSet updatedSamFeatureSet = builder.build();
        assertNotNull(updatedSamFeatureSet);
        assertEquals(
                samFeatureSet.getConditions().size() + 1,
                updatedSamFeatureSet.getConditions().size());
    }

    @Test
    void testCreateObjectUpdateAnnotationList() {
        SamFeatureSet samFeatureSet = createObject();
        // add a new annotation
        SamFeatureSetBuilder builder = SamFeatureSetBuilder.from(samFeatureSet);
        builder.annotationsAdd(AnnotationBuilderTest.createObject());
        SamFeatureSet updatedSamFeatureSet = builder.build();
        assertNotNull(updatedSamFeatureSet);
        assertEquals(
                samFeatureSet.getAnnotations().size() + 1,
                updatedSamFeatureSet.getAnnotations().size());
    }

    @Test
    void testCreateObjectWithOneCondition() {
        // add a new condition
        Condition condition = ConditionBuilderTest.createObject();
        SamFeatureSetBuilder builder = new SamFeatureSetBuilder();
        builder.conditionsAdd(condition);
        SamFeatureSet samFeatureSet = builder.build();
        assertNotNull(samFeatureSet);
        assertEquals(Arrays.asList(condition), samFeatureSet.getConditions());
    }

    @Test
    void testCreateObjectWithAnnotation() {
        Annotation annotation = AnnotationBuilderTest.createObject();
        SamFeatureSetBuilder builder = new SamFeatureSetBuilder();
        builder.annotationsAdd(annotation);
        SamFeatureSet samFeatureSet = builder.build();
        assertNotNull(samFeatureSet);
        assertEquals(Arrays.asList(annotation), samFeatureSet.getAnnotations());
    }

    public static SamFeatureSet createObject(int listSize) {
        List<Condition> conditions = ConditionBuilderTest.createObjects(listSize);
        List<Annotation> annotations = AnnotationBuilderTest.createObjects(listSize);
        SamTrigger samTrigger = SamTriggerBuilderTest.createObject(listSize);
        SamFeatureSetBuilder builder = new SamFeatureSetBuilder();
        builder.conditionsSet(conditions).annotationsSet(annotations);
        builder.samTrigger(samTrigger);

        SamFeatureSet samFeatureSet = builder.build();
        assertNotNull(samFeatureSet);
        assertEquals(conditions, samFeatureSet.getConditions());
        assertEquals(annotations, samFeatureSet.getAnnotations());
        assertEquals(samTrigger, samFeatureSet.getSamTrigger());

        return samFeatureSet;
    }

    public static SamFeatureSet createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<SamFeatureSet> createObjects(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count))
                .collect(Collectors.toList());
    }
}
