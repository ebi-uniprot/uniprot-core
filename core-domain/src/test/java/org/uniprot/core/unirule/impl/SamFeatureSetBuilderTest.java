package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
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

    public static SamFeatureSet createObject() {
        List<Condition> conditions = ConditionBuilderTest.createObjects(4);
        List<Annotation> annotations = AnnotationBuilderTest.createObjects(3);
        SamTrigger samTrigger = SamTriggerBuilderTest.createObject();
        SamFeatureSetBuilder builder = new SamFeatureSetBuilder(samTrigger);
        builder.conditionsSet(conditions).annotationsSet(annotations);

        SamFeatureSet samFeatureSet = builder.build();
        assertNotNull(samFeatureSet);
        assertEquals(conditions, samFeatureSet.getConditions());
        assertEquals(annotations, samFeatureSet.getAnnotations());
        assertEquals(samTrigger, samFeatureSet.getSamTrigger());

        return samFeatureSet;
    }

    public static List<SamFeatureSet> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
