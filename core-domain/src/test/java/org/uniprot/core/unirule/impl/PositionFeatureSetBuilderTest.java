package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilderTest;
import org.uniprot.core.unirule.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PositionFeatureSetBuilderTest {
    @Test
    void testCreateObjectUpdateConditionsList() {
        PositionFeatureSet positionFeatureSet = createObject();
        // add a new condition
        PositionFeatureSetBuilder builder = PositionFeatureSetBuilder.from(positionFeatureSet);
        builder.conditionsAdd(ConditionBuilderTest.createObject());
        PositionFeatureSet updatedPositionFeatureSet = builder.build();
        assertNotNull(updatedPositionFeatureSet);
        assertEquals(
                positionFeatureSet.getConditions().size() + 1,
                updatedPositionFeatureSet.getConditions().size());
    }

    @Test
    void testCreateObjectUpdateAnnotationList() {
        PositionFeatureSet positionFeatureSet = createObject();
        // add a new annotation
        PositionFeatureSetBuilder builder = PositionFeatureSetBuilder.from(positionFeatureSet);
        builder.annotationsAdd(AnnotationBuilderTest.createObject());
        PositionFeatureSet updatedPositionFeatureSet = builder.build();
        assertNotNull(updatedPositionFeatureSet);
        assertEquals(
                positionFeatureSet.getAnnotations().size() + 1,
                updatedPositionFeatureSet.getAnnotations().size());
    }

    @Test
    void testCreateObjectPositionalFeatureList() {
        PositionFeatureSet positionFeatureSet = createObject();
        // add a new PositionalFeature
        PositionFeatureSetBuilder builder = PositionFeatureSetBuilder.from(positionFeatureSet);
        builder.positionalFeaturesAdd(PositionalFeatureBuilderTest.createObject());
        PositionFeatureSet updatedPositionFeatureSet = builder.build();
        assertNotNull(updatedPositionFeatureSet);
        assertEquals(
                positionFeatureSet.getPositionalFeatures().size() + 1,
                updatedPositionFeatureSet.getPositionalFeatures().size());
    }

    @Test
    void testCreateObjectRuleExceptionList() {
        PositionFeatureSet positionFeatureSet = createObject();
        PositionFeatureSetBuilder builder = PositionFeatureSetBuilder.from(positionFeatureSet);
        builder.ruleExceptionsAdd(PositionalRuleExceptionImplTest.createObject());
        PositionFeatureSet updatedPositionFeatureSet = builder.build();
        assertNotNull(updatedPositionFeatureSet);
        assertEquals(
                positionFeatureSet.getRuleExceptions().size() + 1,
                updatedPositionFeatureSet.getRuleExceptions().size());
    }

    @Test
    void testCreateObjectWithCondition() {
        PositionalFeature positionalFeature = PositionalFeatureBuilderTest.createObject();
        PositionFeatureSetBuilder builder = new PositionFeatureSetBuilder(positionalFeature);
        Condition condition = ConditionBuilderTest.createObject();
        builder.conditionsAdd(condition);
        PositionFeatureSet positionFeatureSet = builder.build();
        assertNotNull(positionFeatureSet);
        assertEquals(Arrays.asList(condition), positionFeatureSet.getConditions());
    }

    @Test
    void testCreateObjectWithOneAnnotation() {
        PositionalFeature positionalFeature = PositionalFeatureBuilderTest.createObject();
        PositionFeatureSetBuilder builder = new PositionFeatureSetBuilder(positionalFeature);
        Annotation annotation = AnnotationBuilderTest.createObject();
        builder.annotationsAdd(annotation);
        PositionFeatureSet positionFeatureSet = builder.build();
        assertNotNull(positionFeatureSet);
        assertEquals(Arrays.asList(annotation), positionFeatureSet.getAnnotations());
    }

    @Test
    void testCreateObjectWithOnePositionalFeature() {
        PositionalFeature positionFeature = PositionalFeatureBuilderTest.createObject();
        PositionFeatureSetBuilder builder = new PositionFeatureSetBuilder(positionFeature);
        PositionFeatureSet positionFeatureSet = builder.build();
        assertNotNull(positionFeatureSet);
        assertEquals(Arrays.asList(positionFeature), positionFeatureSet.getPositionalFeatures());
    }

    @Test
    void testCreateObjectWithOneRuleException() {
        PositionalFeature positionalFeature = PositionalFeatureBuilderTest.createObject();
        PositionFeatureSetBuilder builder = new PositionFeatureSetBuilder(positionalFeature);
        RuleException ruleExc = PositionalRuleExceptionImplTest.createObject();
        builder.ruleExceptionsAdd(ruleExc);
        PositionFeatureSet positionFeatureSet = builder.build();
        assertNotNull(positionFeatureSet);
        assertEquals(Arrays.asList(ruleExc), positionFeatureSet.getRuleExceptions());
    }

    public static PositionFeatureSet createObject(int listSize, boolean includeEvidences) {
        String random = UUID.randomUUID().toString();
        String alignmentSignature = "alignmentSignature-" + random;
        String tag = "tag-" + random;
        List<Condition> conditions = ConditionBuilderTest.createObjects(listSize);
        List<Annotation> annotations =
                AnnotationBuilderTest.createObjects(listSize, includeEvidences);
        List<PositionalFeature> positionalFeatures =
                PositionalFeatureBuilderTest.createObjects(listSize);
        List<RuleException> ruleExceptions =
                AnnotationRuleExceptionImplTest.createObjects(listSize, includeEvidences);
        UniProtKBAccession uniProtKBAccession =
                UniProtKBAccessionBuilderTest.createObject(listSize);

        PositionFeatureSetBuilder builder = new PositionFeatureSetBuilder(positionalFeatures);
        builder.alignmentSignature(alignmentSignature).tag(tag);
        builder.conditionsSet(conditions).annotationsSet(annotations);
        builder.ruleExceptionsSet(ruleExceptions);
        builder.uniProtKBAccession(uniProtKBAccession);

        PositionFeatureSet positionFeatureSet = builder.build();
        assertNotNull(positionFeatureSet);
        assertEquals(alignmentSignature, positionFeatureSet.getAlignmentSignature());
        assertEquals(tag, positionFeatureSet.getTag());
        assertEquals(conditions, positionFeatureSet.getConditions());
        assertEquals(annotations, positionFeatureSet.getAnnotations());
        assertEquals(positionalFeatures, positionFeatureSet.getPositionalFeatures());
        assertEquals(ruleExceptions, positionFeatureSet.getRuleExceptions());
        assertEquals(uniProtKBAccession, positionFeatureSet.getUniProtKBAccession());

        return positionFeatureSet;
    }

    public static PositionFeatureSet createObject(int listSize) {
        return createObject(listSize, false);
    }

    public static PositionFeatureSet createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<PositionFeatureSet> createObjects(int count) {
        return createObjects(count, false);
    }

    public static List<PositionFeatureSet> createObjects(int count, boolean includeEvidences) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count, includeEvidences))
                .collect(Collectors.toList());
    }
}
