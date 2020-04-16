package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.unirule.*;

public class UniRuleEntryImplTest {
    @Test
    void testCreateObjectByNoArgConstructor() {
        UniRuleEntry uniRuleEntry = new UniRuleEntryImpl();
        assertNotNull(uniRuleEntry);
        assertTrue(uniRuleEntry.getOtherRules().isEmpty());
        assertTrue(uniRuleEntry.getSamFeatureSets().isEmpty());
        assertTrue(uniRuleEntry.getPositionFeatureSets().isEmpty());
        assertNull(uniRuleEntry.getUniRuleId());
        assertNull(uniRuleEntry.getInformation());
        assertNull(uniRuleEntry.getRuleStatus());
        assertNull(uniRuleEntry.getMainRule());
        assertNull(uniRuleEntry.getCreatedBy());
        assertNull(uniRuleEntry.getModifiedBy());
        assertNull(uniRuleEntry.getCreatedDate());
        assertNull(uniRuleEntry.getModifiedDate());
    }

    @Test
    void testCreateObject() {
        UniRuleId uniRuleId = new UniRuleIdBuilder("UR123456789").build();
        Information information = new InformationBuilder().build();
        RuleStatus ruleStatus = RuleStatus.APPLY;
        Condition condition1 = new ConditionBuilder("type1").build();
        Condition condition2 = new ConditionBuilder("type2").build();
        Condition condition3 = new ConditionBuilder("type3").build();
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

        Rule mainRule = new RuleBuilder(conditionSets, annotations, ruleExceptions).build();
        CaseRule caseRule = new CaseRuleBuilder(conditionSets, annotations, ruleExceptions).build();
        List<CaseRule> otherRules = Arrays.asList(caseRule);
        SamFeatureSet samFeatureSet1 = new SamFeatureSetBuilder().build();
        SamFeatureSet samFeatureSet2 = new SamFeatureSetBuilder().build();
        List<SamFeatureSet> samFeatureSets = Arrays.asList(samFeatureSet1, samFeatureSet2);
        PositionFeatureSet positionFeatureSet1 = new PositionFeatureSetBuilder().tag("t1").build();
        PositionFeatureSet positionFeatureSet2 = new PositionFeatureSetBuilder().tag("t2").build();
        List<PositionFeatureSet> positionFeatureSets =
                Arrays.asList(positionFeatureSet1, positionFeatureSet2);
        String createdBy = "sample author1";
        String modifiedBy = "sample author2";
        Date createdDate = new Date();
        Date modifiedDate = new Date();

        UniRuleEntry uniRuleEntry =
                new UniRuleEntryImpl(
                        uniRuleId,
                        information,
                        ruleStatus,
                        mainRule,
                        otherRules,
                        samFeatureSets,
                        positionFeatureSets,
                        createdBy,
                        modifiedBy,
                        createdDate,
                        modifiedDate);
        assertNotNull(uniRuleEntry);
        assertEquals(uniRuleId, uniRuleEntry.getUniRuleId());
        assertEquals(information, uniRuleEntry.getInformation());
        assertEquals(ruleStatus, uniRuleEntry.getRuleStatus());
        assertEquals(mainRule, uniRuleEntry.getMainRule());
        assertEquals(otherRules, uniRuleEntry.getOtherRules());
        assertEquals(samFeatureSets, uniRuleEntry.getSamFeatureSets());
        assertEquals(positionFeatureSets, uniRuleEntry.getPositionFeatureSets());
        assertEquals(createdBy, uniRuleEntry.getCreatedBy());
        assertEquals(modifiedBy, uniRuleEntry.getModifiedBy());
        assertEquals(createdDate, uniRuleEntry.getCreatedDate());
        assertEquals(modifiedDate, uniRuleEntry.getModifiedDate());
    }
}
