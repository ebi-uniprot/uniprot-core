package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Range;
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
        Information information = new InformationBuilder("version").build();
        RuleStatus ruleStatus = RuleStatus.APPLY;
        Condition condition1 = new ConditionBuilder("type1").build();
        Condition condition2 = new ConditionBuilder("type2").build();
        Condition condition3 = new ConditionBuilder("type3").build();
        List<Condition> conditions = Arrays.asList(condition1, condition2, condition3);
        ConditionSet conditionSet = new ConditionSetBuilder(conditions).build();
        List<ConditionSet> conditionSets = Arrays.asList(conditionSet);

        Annotation annotation1 = new AnnotationBuilder().build();
        Annotation annotation2 = new AnnotationBuilder().build();
        List<Annotation> annotations = Arrays.asList(annotation1, annotation2);
        String note = "sample note";
        String category = "sample category";
        String accessionValue = "P12345";
        UniProtKBAccession accession = new UniProtKBAccessionBuilder(accessionValue).build();
        List<UniProtKBAccession> accessionList = Arrays.asList(accession);
        Range position = new Range(1, 2);
        PositionalFeature positionalFeature = new PositionalFeatureBuilder(position).build();
        RuleException ruleException2 =
                new RuleExceptionImpl(note, category, positionalFeature, accessionList);
        List<RuleException> ruleExceptions = new ArrayList<>();
        ruleExceptions.add(ruleException2);

        Rule mainRule =
                new RuleBuilder(conditionSets)
                        .annotationsSet(annotations)
                        .ruleExceptionsSet(ruleExceptions)
                        .build();

        CaseRuleBuilder caseRuleBuilder = new CaseRuleBuilder(conditionSets);
        caseRuleBuilder.annotationsSet(annotations).ruleExceptionsSet(ruleExceptions);

        CaseRule caseRule = caseRuleBuilder.build();

        List<CaseRule> otherRules = Arrays.asList(caseRule);
        SamFeatureSet samFeatureSet1 =
                new SamFeatureSetBuilder(SamTriggerBuilderTest.createObject()).build();
        SamFeatureSet samFeatureSet2 =
                new SamFeatureSetBuilder(SamTriggerBuilderTest.createObject()).build();
        List<SamFeatureSet> samFeatureSets = Arrays.asList(samFeatureSet1, samFeatureSet2);
        PositionalFeature pf1 = PositionalFeatureBuilderTest.createObject();
        PositionalFeature pf2 = PositionalFeatureBuilderTest.createObject();
        PositionFeatureSet positionFeatureSet1 = new PositionFeatureSetBuilder(pf1).build();
        PositionFeatureSet positionFeatureSet2 = new PositionFeatureSetBuilder(pf2).build();
        List<PositionFeatureSet> positionFeatureSets =
                Arrays.asList(positionFeatureSet1, positionFeatureSet2);
        String createdBy = "sample author1";
        String modifiedBy = "sample author2";
        LocalDate createdDate = LocalDate.now();
        LocalDate modifiedDate = LocalDate.now();
        Long proteinsAnnotatedCount = ThreadLocalRandom.current().nextLong();

        UniRuleEntry uniRuleEntry =
                new UniRuleEntryImpl(
                        uniRuleId,
                        information,
                        ruleStatus,
                        mainRule,
                        otherRules,
                        samFeatureSets,
                        positionFeatureSets,
                        proteinsAnnotatedCount,
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
        assertEquals(proteinsAnnotatedCount, uniRuleEntry.getProteinsAnnotatedCount());
        assertEquals(createdBy, uniRuleEntry.getCreatedBy());
        assertEquals(modifiedBy, uniRuleEntry.getModifiedBy());
        assertEquals(createdDate, uniRuleEntry.getCreatedDate());
        assertEquals(modifiedDate, uniRuleEntry.getModifiedDate());
    }
}
