package org.uniprot.core.unirule.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.SamFeatureSet;
import org.uniprot.core.unirule.SamTrigger;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SamFeatureSetBuilderTest {
    @Test
    void testCreateObjectUpdateConditionsList(){
        SamFeatureSet samFeatureSet = createObject();
        // add a new condition
        SamFeatureSetBuilder builder = SamFeatureSetBuilder.from(samFeatureSet);
        builder.conditionsAdd(ConditionBuilderTest.createObject());
        SamFeatureSet updatedSamFeatureSet = builder.build();
        assertNotNull(updatedSamFeatureSet);
        assertEquals(samFeatureSet.getConditions().size() + 1, updatedSamFeatureSet.getConditions().size());
    }

    @Test
    void testCreateObjectUpdateAnnotationList(){
        SamFeatureSet samFeatureSet = createObject();
        // add a new annotation
        SamFeatureSetBuilder builder = SamFeatureSetBuilder.from(samFeatureSet);
        builder.annotationsAdd(AnnotationBuilderTest.createObject());
        SamFeatureSet updatedSamFeatureSet = builder.build();
        assertNotNull(updatedSamFeatureSet);
        assertEquals(samFeatureSet.getAnnotations().size() + 1, updatedSamFeatureSet.getAnnotations().size());
    }


    public static SamFeatureSet createObject() {
        List<Condition> conditions = ConditionBuilderTest.createObjects(4);
        List<Annotation> annotations = AnnotationBuilderTest.createObjects(3);
        SamTrigger samTrigger = SamTriggerBuilderTest.createObject();
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

    public static List<SamFeatureSet> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
