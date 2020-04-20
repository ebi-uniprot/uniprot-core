package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.*;

public class RuleBuilderTest {

    @Test
    void testConditionSetListAppend() {
        Rule object = createObject();
        RuleBuilder builder = RuleBuilder.from(object);
        // append one item to the list
        ConditionSet conditionSet = ConditionSetBuilderTest.createObject();
        builder.conditionSetsAdd(conditionSet);
        Rule updatedObject = builder.build();
        assertNotNull(updatedObject);
        assertEquals(object.getConditionSets().size() + 1, updatedObject.getConditionSets().size());
    }

    @Test
    void testAnnotationListAppend() {
        Rule object = createObject();
        RuleBuilder builder = RuleBuilder.from(object);
        // append an item to the list
        Annotation annotation = AnnotationBuilderTest.createObject();
        builder.annotationsAdd(annotation);
        Rule updatedObject = builder.build();
        assertNotNull(updatedObject);
        assertEquals(object.getAnnotations().size() + 1, updatedObject.getAnnotations().size());
    }

    @Test
    void testRuleExceptionListAppend() {
        Rule object = createObject();
        RuleBuilder builder = RuleBuilder.from(object);
        // append one item to the list
        RuleException<Annotation> ruleException = AnnotationRuleExceptionImplTest.createObject();
        builder.ruleExceptionsAdd(ruleException);
        Rule updatedObject = builder.build();
        assertNotNull(updatedObject);
        assertEquals(
                object.getRuleExceptions().size() + 1, updatedObject.getRuleExceptions().size());
    }

    @Test
    void testAddConditionSetList() {
        RuleBuilder builder = new RuleBuilder();
        // add one item to the empty list
        ConditionSet conditionSet = ConditionSetBuilderTest.createObject();
        builder.conditionSetsAdd(conditionSet);
        Rule object = builder.build();
        assertNotNull(object);
        assertEquals(Arrays.asList(conditionSet), object.getConditionSets());
    }

    @Test
    void testAddAnnotationList() {
        RuleBuilder builder = new RuleBuilder();
        // add one item to the empty list
        Annotation annotation = AnnotationBuilderTest.createObject();
        builder.annotationsAdd(annotation);
        Rule object = builder.build();
        assertNotNull(object);
        assertEquals(Arrays.asList(annotation), object.getAnnotations());
    }

    @Test
    void testAddRuleExceptionList() {
        RuleBuilder builder = new RuleBuilder();
        // add one item to the empty list
        RuleException<Annotation> ruleException = AnnotationRuleExceptionImplTest.createObject();
        builder.ruleExceptionsAdd(ruleException);
        Rule object = builder.build();
        assertNotNull(object);
        assertEquals(Arrays.asList(ruleException), object.getRuleExceptions());
    }

    public static Rule createObject() {
        List<ConditionSet> conditionSets = ConditionSetBuilderTest.createObjects(2);
        List<Annotation> annotations = AnnotationBuilderTest.createObjects(3);
        List<RuleException<PositionalFeature>> ruleExceptions =
                PositionalRuleExceptionImplTest.createObjects(3);
        RuleBuilder<PositionalFeature> builder = new RuleBuilder<>();
        builder.conditionSetsSet(conditionSets);
        builder.annotationsSet(annotations);
        builder.ruleExceptionsSet(ruleExceptions);
        Rule rule = builder.build();
        assertNotNull(rule);
        assertEquals(conditionSets, rule.getConditionSets());
        assertEquals(annotations, rule.getAnnotations());
        assertEquals(ruleExceptions, rule.getRuleExceptions());
        return rule;
    }

    public static List<Rule> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
