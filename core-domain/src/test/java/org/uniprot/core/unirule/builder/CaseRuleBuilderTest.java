package org.uniprot.core.unirule.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.CaseRule;
import org.uniprot.core.unirule.ConditionSet;
import org.uniprot.core.unirule.RuleException;
import org.uniprot.core.unirule.impl.AnnotationRuleExceptionImplTest;

public class CaseRuleBuilderTest {

    public static CaseRule createObject() {
        List<ConditionSet> conditionSets = ConditionSetBuilderTest.createObjects(2);
        List<Annotation> annotations = AnnotationBuilderTest.createObjects(3);
        List<RuleException<Annotation>> ruleExceptions =
                AnnotationRuleExceptionImplTest.createObjects(3);
        CaseRuleBuilder<Annotation> builder =
                new CaseRuleBuilder<>(conditionSets, annotations, ruleExceptions);
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
