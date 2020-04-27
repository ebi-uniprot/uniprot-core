package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.CaseRule;
import org.uniprot.core.unirule.ConditionSet;
import org.uniprot.core.unirule.RuleException;

public class CaseRuleBuilderTest {

    @Test
    void testCreateObjectWithNullMandatoryParam() {
        CaseRuleBuilder builder = new CaseRuleBuilder((ConditionSet) null);
        assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void testCreateObjectWithNullMandatoryParamList() {
        CaseRuleBuilder builder = new CaseRuleBuilder((List<ConditionSet>) null);
        assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void testSetStatsExempted() {
        List<ConditionSet> conditionSetList = new ArrayList<>();
        conditionSetList.add(ConditionSetBuilderTest.createObject());
        CaseRuleBuilder builder = new CaseRuleBuilder(conditionSetList);
        builder.overallStatsExempted(true);
        CaseRule object = builder.build();
        assertNotNull(object);
        assertTrue(object.isOverallStatsExempted());
    }

    @Test
    void testConditionSetListAppend() {
        CaseRule object = createObject();
        CaseRuleBuilder builder = CaseRuleBuilder.from(object);
        // append one item to the list
        ConditionSet conditionSet = ConditionSetBuilderTest.createObject();
        builder.conditionSetsAdd(conditionSet);
        CaseRule updatedObject = builder.build();
        assertNotNull(updatedObject);
        assertEquals(object.getConditionSets().size() + 1, updatedObject.getConditionSets().size());
    }

    @Test
    void testAnnotationListAppend() {
        CaseRule object = createObject();
        CaseRuleBuilder builder = CaseRuleBuilder.from(object);
        // append an item to the list
        Annotation annotation = AnnotationBuilderTest.createObject();
        builder.annotationsAdd(annotation);
        CaseRule updatedObject = builder.build();
        assertNotNull(updatedObject);
        assertEquals(object.getAnnotations().size() + 1, updatedObject.getAnnotations().size());
    }

    @Test
    void testRuleExceptionListAppend() {
        CaseRule object = createObject();
        CaseRuleBuilder builder = CaseRuleBuilder.from(object);
        // append one item to the list
        RuleException<Annotation> ruleException = AnnotationRuleExceptionImplTest.createObject();
        builder.ruleExceptionsAdd(ruleException);
        CaseRule updatedObject = builder.build();
        assertNotNull(updatedObject);
        assertEquals(
                object.getRuleExceptions().size() + 1, updatedObject.getRuleExceptions().size());
    }

    @Test
    void testAddConditionSetList() {
        List<ConditionSet> conditionSetList = new ArrayList<>();
        conditionSetList.add(ConditionSetBuilderTest.createObject());
        CaseRuleBuilder builder = new CaseRuleBuilder(conditionSetList);
        // add one item to the empty list
        ConditionSet conditionSet = ConditionSetBuilderTest.createObject();
        builder.conditionSetsAdd(conditionSet);
        CaseRule object = builder.build();
        assertNotNull(object);
        conditionSetList.add(conditionSet);
        assertEquals(conditionSetList, object.getConditionSets());
    }

    @Test
    void testAddAnnotationList() {
        List<ConditionSet> conditionSetList = new ArrayList<>();
        conditionSetList.add(ConditionSetBuilderTest.createObject());
        CaseRuleBuilder builder = new CaseRuleBuilder(conditionSetList);
        // add one item to the empty list
        Annotation annotation = AnnotationBuilderTest.createObject();
        builder.annotationsAdd(annotation);
        CaseRule object = builder.build();
        assertNotNull(object);
        assertEquals(Arrays.asList(annotation), object.getAnnotations());
    }

    @Test
    void testAddRuleExceptionList() {
        List<ConditionSet> conditionSetList = new ArrayList<>();
        conditionSetList.add(ConditionSetBuilderTest.createObject());
        CaseRuleBuilder builder = new CaseRuleBuilder(conditionSetList);
        // add one item to the empty list
        RuleException<Annotation> ruleException = AnnotationRuleExceptionImplTest.createObject();
        builder.ruleExceptionsAdd(ruleException);
        CaseRule object = builder.build();
        assertNotNull(object);
        assertEquals(Arrays.asList(ruleException), object.getRuleExceptions());
    }

    public static CaseRule createObject(int listSize) {
        List<ConditionSet> conditionSets = ConditionSetBuilderTest.createObjects(listSize);
        List<Annotation> annotations = AnnotationBuilderTest.createObjects(listSize);
        List<RuleException<Annotation>> ruleExceptions =
                AnnotationRuleExceptionImplTest.createObjects(listSize);
        CaseRuleBuilder<Annotation> builder = new CaseRuleBuilder<>(conditionSets);
        builder.annotationsSet(annotations);
        builder.ruleExceptionsSet(ruleExceptions);
        boolean exempted = true;
        builder.overallStatsExempted(exempted);
        CaseRule rule = builder.build();
        assertNotNull(rule);
        assertEquals(conditionSets, rule.getConditionSets());
        assertEquals(annotations, rule.getAnnotations());
        assertEquals(ruleExceptions, rule.getRuleExceptions());
        assertEquals(exempted, rule.isOverallStatsExempted());
        return rule;
    }

    public static CaseRule createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<CaseRule> createObjects(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count))
                .collect(Collectors.toList());
    }
}
