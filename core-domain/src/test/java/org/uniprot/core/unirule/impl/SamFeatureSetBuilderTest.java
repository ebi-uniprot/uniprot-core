package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

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
    void testCreateObjectWithNullMandatoryParam() {
        SamFeatureSetBuilder builder = new SamFeatureSetBuilder(null);
        assertThrows(IllegalArgumentException.class, builder::build);
    }

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
        SamFeatureSetBuilder builder =
                new SamFeatureSetBuilder(SamTriggerBuilderTest.createObject());
        builder.conditionsAdd(condition);
        SamFeatureSet samFeatureSet = builder.build();
        assertNotNull(samFeatureSet);
        assertEquals(Arrays.asList(condition), samFeatureSet.getConditions());
    }

    @Test
    void testCreateObjectWithAnnotation() {
        Annotation annotation = AnnotationBuilderTest.createObject();
        SamFeatureSetBuilder builder =
                new SamFeatureSetBuilder(SamTriggerBuilderTest.createObject());
        builder.annotationsAdd(annotation);
        SamFeatureSet samFeatureSet = builder.build();
        assertNotNull(samFeatureSet);
        assertEquals(Arrays.asList(annotation), samFeatureSet.getAnnotations());
    }

    @Test
    void testUpdateSameTrigger() {
        SamFeatureSetBuilder builder =
                new SamFeatureSetBuilder(SamTriggerBuilderTest.createObject());
        SamTrigger st = SamTriggerBuilderTest.createObject();
        builder.samTrigger(st);
        SamFeatureSet samFeatureSet = builder.build();
        assertNotNull(samFeatureSet);
        assertEquals(st, samFeatureSet.getSamTrigger());
    }

    public static SamFeatureSet createObject(int listSize, boolean includeEvidences) {
        List<Condition> conditions = ConditionBuilderTest.createObjects(listSize);
        List<Annotation> annotations =
                AnnotationBuilderTest.createObjects(listSize, includeEvidences);
        SamTrigger samTrigger = SamTriggerBuilderTest.createObject(listSize);
        SamFeatureSetBuilder builder = new SamFeatureSetBuilder(samTrigger);
        builder.conditionsSet(conditions).annotationsSet(annotations);

        SamFeatureSet samFeatureSet = builder.build();
        assertNotNull(samFeatureSet);
        assertEquals(conditions, samFeatureSet.getConditions());
        assertEquals(annotations, samFeatureSet.getAnnotations());
        assertEquals(samTrigger, samFeatureSet.getSamTrigger());

        return samFeatureSet;
    }

    public static SamFeatureSet createObject(int listSize) {
        return createObject(listSize, false);
    }

    public static SamFeatureSet createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<SamFeatureSet> createObjects(int count) {
        return createObjects(count, false);
    }

    public static List<SamFeatureSet> createObjects(int count, boolean includeEvidences) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count, includeEvidences))
                .collect(Collectors.toList());
    }
}
