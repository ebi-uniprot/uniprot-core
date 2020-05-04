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
import org.uniprot.core.unirule.ConditionSet;
import org.uniprot.core.unirule.Rule;
import org.uniprot.core.unirule.RuleException;

public class RuleBuilderTest {

    @Test
    void testCreateObjectWithNullMandatoryParam() {
        RuleBuilder builder = new RuleBuilder((ConditionSet) null);
        assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void testCreateObjectWithNullMandatoryParamList() {
        RuleBuilder builder = new RuleBuilder((List<ConditionSet>) null);
        assertThrows(IllegalArgumentException.class, builder::build);
    }

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
        RuleException ruleException = AnnotationRuleExceptionImplTest.createObject();
        builder.ruleExceptionsAdd(ruleException);
        Rule updatedObject = builder.build();
        assertNotNull(updatedObject);
        assertEquals(
                object.getRuleExceptions().size() + 1, updatedObject.getRuleExceptions().size());
    }

    @Test
    void testAddConditionSetList() {
        List<ConditionSet> csList = new ArrayList<>();
        csList.add(ConditionSetBuilderTest.createObject());
        RuleBuilder builder = new RuleBuilder(csList);
        // add one item to the empty list
        ConditionSet conditionSet = ConditionSetBuilderTest.createObject();
        builder.conditionSetsAdd(conditionSet);
        Rule object = builder.build();
        assertNotNull(object);
        csList.add(conditionSet);
        assertEquals(csList, object.getConditionSets());
    }

    @Test
    void testAddAnnotationList() {
        List<ConditionSet> csList = new ArrayList<>();
        csList.add(ConditionSetBuilderTest.createObject());
        RuleBuilder builder = new RuleBuilder(csList);
        // add one item to the empty list
        Annotation annotation = AnnotationBuilderTest.createObject();
        builder.annotationsAdd(annotation);
        Rule object = builder.build();
        assertNotNull(object);
        assertEquals(Arrays.asList(annotation), object.getAnnotations());
    }

    @Test
    void testAddRuleExceptionList() {
        List<ConditionSet> csList = new ArrayList<>();
        csList.add(ConditionSetBuilderTest.createObject());
        RuleBuilder builder = new RuleBuilder(csList);
        // add one item to the empty list
        RuleException ruleException = AnnotationRuleExceptionImplTest.createObject();
        builder.ruleExceptionsAdd(ruleException);
        Rule object = builder.build();
        assertNotNull(object);
        assertEquals(Arrays.asList(ruleException), object.getRuleExceptions());
    }

    @Test
    void testSetConditionSetList() {
        List<ConditionSet> csList = new ArrayList<>();
        csList.add(ConditionSetBuilderTest.createObject());
        RuleBuilder builder = new RuleBuilder(csList);
        List<ConditionSet> conditionSets = ConditionSetBuilderTest.createObjects(2);
        builder.conditionSetsSet(conditionSets);
        Rule object = builder.build();
        assertNotNull(object);
        assertEquals(conditionSets, object.getConditionSets());
    }

    public static Rule createObject(int listSize, boolean includeEvidences) {
        List<ConditionSet> conditionSets = ConditionSetBuilderTest.createObjects(listSize);
        List<Annotation> annotations =
                AnnotationBuilderTest.createObjects(listSize, includeEvidences);
        List<RuleException> ruleExceptions =
                AnnotationRuleExceptionImplTest.createObjects(listSize, includeEvidences);
        RuleBuilder builder = new RuleBuilder(conditionSets);
        builder.annotationsSet(annotations);
        builder.ruleExceptionsSet(ruleExceptions);
        Rule rule = builder.build();
        assertNotNull(rule);
        assertEquals(conditionSets, rule.getConditionSets());
        assertEquals(annotations, rule.getAnnotations());
        assertEquals(ruleExceptions, rule.getRuleExceptions());
        return rule;
    }

    public static Rule createObject(int listSize) {
        return createObject(listSize, false);
    }

    public static Rule createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<Rule> createObjects(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count))
                .collect(Collectors.toList());
    }
}
