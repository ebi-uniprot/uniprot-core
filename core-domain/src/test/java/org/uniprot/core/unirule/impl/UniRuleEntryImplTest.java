package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Range;
import org.uniprot.core.Statistics;
import org.uniprot.core.impl.StatisticsBuilder;
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
        assertNull(uniRuleEntry.getMainRule());
        assertNull(uniRuleEntry.getCreatedDate());
        assertNull(uniRuleEntry.getModifiedDate());
    }

    @Test
    void testCreateObject() {
        UniRuleId uniRuleId = new UniRuleIdBuilder("UR123456789").build();
        Information information = new InformationBuilder("version").build();
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
        Statistics statistics =
                new StatisticsBuilder()
                        .reviewedProteinCount(ThreadLocalRandom.current().nextLong())
                        .unreviewedProteinCount(ThreadLocalRandom.current().nextLong())
                        .build();

        UniRuleEntry uniRuleEntry =
                new UniRuleEntryImpl(
                        uniRuleId,
                        information,
                        mainRule,
                        otherRules,
                        samFeatureSets,
                        positionFeatureSets,
                        statistics,
                        createdDate,
                        modifiedDate);
        assertNotNull(uniRuleEntry);
        assertEquals(uniRuleId, uniRuleEntry.getUniRuleId());
        assertEquals(information, uniRuleEntry.getInformation());
        assertEquals(mainRule, uniRuleEntry.getMainRule());
        assertEquals(otherRules, uniRuleEntry.getOtherRules());
        assertEquals(samFeatureSets, uniRuleEntry.getSamFeatureSets());
        assertEquals(positionFeatureSets, uniRuleEntry.getPositionFeatureSets());
        assertEquals(statistics, uniRuleEntry.getStatistics());
        assertEquals(createdDate, uniRuleEntry.getCreatedDate());
        assertEquals(modifiedDate, uniRuleEntry.getModifiedDate());
    }
}
