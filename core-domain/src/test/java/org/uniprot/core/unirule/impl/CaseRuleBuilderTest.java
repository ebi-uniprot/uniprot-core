package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

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
    void testSetStatsExempted() {
        CaseRuleBuilder builder = new CaseRuleBuilder();
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
        CaseRuleBuilder builder = new CaseRuleBuilder();
        // add one item to the empty list
        ConditionSet conditionSet = ConditionSetBuilderTest.createObject();
        builder.conditionSetsAdd(conditionSet);
        CaseRule object = builder.build();
        assertNotNull(object);
        assertEquals(Arrays.asList(conditionSet), object.getConditionSets());
    }

    @Test
    void testAddAnnotationList() {
        CaseRuleBuilder builder = new CaseRuleBuilder();
        // add one item to the empty list
        Annotation annotation = AnnotationBuilderTest.createObject();
        builder.annotationsAdd(annotation);
        CaseRule object = builder.build();
        assertNotNull(object);
        assertEquals(Arrays.asList(annotation), object.getAnnotations());
    }

    @Test
    void testAddRuleExceptionList() {
        CaseRuleBuilder builder = new CaseRuleBuilder();
        // add one item to the empty list
        RuleException<Annotation> ruleException = AnnotationRuleExceptionImplTest.createObject();
        builder.ruleExceptionsAdd(ruleException);
        CaseRule object = builder.build();
        assertNotNull(object);
        assertEquals(Arrays.asList(ruleException), object.getRuleExceptions());
    }

    public static CaseRule createObject() {
        List<ConditionSet> conditionSets = ConditionSetBuilderTest.createObjects(2);
        List<Annotation> annotations = AnnotationBuilderTest.createObjects(3);
        List<RuleException<Annotation>> ruleExceptions =
                AnnotationRuleExceptionImplTest.createObjects(3);
        CaseRuleBuilder<Annotation> builder = new CaseRuleBuilder<>();
        builder.conditionSetsSet(conditionSets).annotationsSet(annotations);
        builder.ruleExceptionsSet(ruleExceptions);
        boolean exempted = ThreadLocalRandom.current().nextBoolean();
        builder.overallStatsExempted(exempted);
        CaseRule rule = builder.build();
        assertNotNull(rule);
        assertEquals(conditionSets, rule.getConditionSets());
        assertEquals(annotations, rule.getAnnotations());
        assertEquals(ruleExceptions, rule.getRuleExceptions());
        assertEquals(exempted, rule.isOverallStatsExempted());
        return rule;
    }

    public static List<CaseRule> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
