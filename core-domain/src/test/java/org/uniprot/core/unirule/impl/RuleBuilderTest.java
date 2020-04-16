package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.uniprot.core.unirule.*;

public class RuleBuilderTest {

    public static Rule createObject() {
        List<ConditionSet> conditionSets = ConditionSetBuilderTest.createObjects(2);
        List<Annotation> annotations = AnnotationBuilderTest.createObjects(3);
        List<RuleException<PositionalFeature>> ruleExceptions =
                PositionalRuleExceptionImplTest.createObjects(3);
        RuleBuilder<PositionalFeature> builder =
                new RuleBuilder<>(conditionSets, annotations, ruleExceptions);
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
