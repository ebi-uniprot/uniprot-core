package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.unirule.*;
import org.uniprot.core.unirule.builder.AnnotationBuilder;
import org.uniprot.core.unirule.builder.ConditionBuilder;
import org.uniprot.core.unirule.builder.ConditionSetBuilder;
import org.uniprot.core.unirule.builder.PositionalFeatureBuilder;

public class CaseRuleImplTest {
    @Test
    void testCreateObjectByNoArgConstructor() {
        CaseRule rule = new CaseRuleImpl();
        assertNotNull(rule);
        assertTrue(rule.getAnnotations().isEmpty());
        assertTrue(rule.getConditionSets().isEmpty());
        assertTrue(rule.getRuleExceptions().isEmpty());
        assertFalse(rule.isOverallStatsExempted());
    }

    @Test
    void testCreateObject() {
        Condition condition1 = new ConditionBuilder().type("type1").build();
        Condition condition2 = new ConditionBuilder().type("type2").build();
        Condition condition3 = new ConditionBuilder().type("type3").build();
        List<Condition> conditions = Arrays.asList(condition1, condition2, condition3);
        ConditionSet conditionSet = new ConditionSetBuilder().conditionsSet(conditions).build();
        List<ConditionSet> conditionSets = Arrays.asList(conditionSet);

        Annotation annotation1 = new AnnotationBuilder().build();
        Annotation annotation2 = new AnnotationBuilder().build();
        List<Annotation> annotations = Arrays.asList(annotation1, annotation2);

        String note = "sample note";
        String category = "sample category";
        String accessionValue = "P12345";
        Annotation annotation = new AnnotationBuilder().build();
        UniProtKBAccession accession = new UniProtKBAccessionBuilder(accessionValue).build();
        List<UniProtKBAccession> accessionList = Arrays.asList(accession);
        RuleException<Annotation> ruleException1 =
                new AnnotationRuleExceptionImpl(note, category, annotation, accessionList);

        PositionalFeature positionalFeature = new PositionalFeatureBuilder().build();
        RuleException<PositionalFeature> ruleException2 =
                new PositionalRuleExceptionImpl(note, category, positionalFeature, accessionList);
        List<RuleException> ruleExceptions = Arrays.asList(ruleException1, ruleException2);
        boolean isOverallStatsExempted = true;
        CaseRule rule =
                new CaseRuleImpl(
                        conditionSets, annotations, ruleExceptions, isOverallStatsExempted);
        assertNotNull(rule);
        assertEquals(conditionSets, rule.getConditionSets());
        assertEquals(annotations, rule.getAnnotations());
        assertEquals(ruleExceptions, rule.getRuleExceptions());
        assertTrue(rule.isOverallStatsExempted());
    }
}
