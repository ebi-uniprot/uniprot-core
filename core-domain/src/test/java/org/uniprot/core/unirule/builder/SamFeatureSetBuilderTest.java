package org.uniprot.core.unirule.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.SamFeatureSet;
import org.uniprot.core.unirule.SamTrigger;

public class SamFeatureSetBuilderTest {
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
