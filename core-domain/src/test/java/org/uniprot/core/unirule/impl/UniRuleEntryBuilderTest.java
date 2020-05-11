package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.*;

public class UniRuleEntryBuilderTest {

    @Test
    void testCreateObjectWithNullMandatoryParams() {
        UniRuleEntryBuilder builder = new UniRuleEntryBuilder(null, null, null, null);
        assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void testCreateObjectWithNullMandatoryParamRuleId() {
        UniRuleEntryBuilder builder =
                new UniRuleEntryBuilder(
                        null,
                        RuleStatus.APPLY,
                        InformationBuilderTest.createObject(),
                        RuleBuilderTest.createObject());
        assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void testCreateObjectWithNullMandatoryParamRuleStatus() {
        UniRuleEntryBuilder builder =
                new UniRuleEntryBuilder(
                        UniRuleIdBuilderTest.createObject(),
                        null,
                        InformationBuilderTest.createObject(),
                        RuleBuilderTest.createObject());
        assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void testCreateObjectWithNullMandatoryParamInformation() {
        UniRuleEntryBuilder builder =
                new UniRuleEntryBuilder(
                        UniRuleIdBuilderTest.createObject(),
                        RuleStatus.APPLY,
                        null,
                        RuleBuilderTest.createObject());
        assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void testCreateObjectWithNullMandatoryParamMainRule() {
        UniRuleEntryBuilder builder =
                new UniRuleEntryBuilder(
                        UniRuleIdBuilderTest.createObject(),
                        RuleStatus.APPLY,
                        InformationBuilderTest.createObject(),
                        null);
        assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void testCreateObjectUpdateMandatoryParamRuleStatus() {
        UniRuleEntry entry = createObject();
        assertNotNull(entry);
        UniRuleEntryBuilder builder = UniRuleEntryBuilder.from(entry);
        // update status
        RuleStatus rs1 = RuleStatus.APPLY;
        builder.ruleStatus(rs1);
        UniRuleEntry updatedEntry = builder.build();
        assertNotNull(updatedEntry);
        assertEquals(rs1, updatedEntry.getRuleStatus());

        builder = UniRuleEntryBuilder.from(updatedEntry);
        // update status again if there is collision in selection
        RuleStatus rs2 = RuleStatus.TEST;
        builder.ruleStatus(rs2);
        updatedEntry = builder.build();
        assertNotNull(updatedEntry);
        assertEquals(rs2, updatedEntry.getRuleStatus());
    }

    @Test
    void testCreateObjectUpdateMandatoryParamRuleId() {
        UniRuleEntry entry = createObject();
        assertNotNull(entry);
        UniRuleEntryBuilder builder = UniRuleEntryBuilder.from(entry);
        // update rule id
        UniRuleId rId = UniRuleIdBuilderTest.createObject();
        builder.uniRuleId(rId);
        UniRuleEntry updatedEntry = builder.build();
        assertNotNull(updatedEntry);
        assertEquals(rId, updatedEntry.getUniRuleId());
        assertNotEquals(entry.getUniRuleId(), updatedEntry.getUniRuleId());
    }

    @Test
    void testCreateObjectOverwriteOtherRulesList() {
        UniRuleEntry uniRuleEntry = createObject();
        assertFalse(uniRuleEntry.getOtherRules().isEmpty());
        UniRuleEntryBuilder builder = UniRuleEntryBuilder.from(uniRuleEntry);
        // overwrite  rules
        List<CaseRule> rules = CaseRuleBuilderTest.createObjects(1);
        builder.otherRulesSet(rules);
        UniRuleEntry updatedUniProtEntry = builder.build();
        assertNotNull(updatedUniProtEntry);
        assertEquals(1, updatedUniProtEntry.getOtherRules().size());
        assertEquals(rules, updatedUniProtEntry.getOtherRules());
    }

    @Test
    void testCreateObjectUpdateOtherRulesList() {
        UniRuleEntry uniRuleEntry = createObject();
        UniRuleEntryBuilder builder = UniRuleEntryBuilder.from(uniRuleEntry);
        // add couple of case rules
        CaseRule rule1 = CaseRuleBuilderTest.createObject();
        CaseRule rule2 = CaseRuleBuilderTest.createObject();
        builder.otherRulesAdd(rule1).otherRulesAdd(rule2);
        UniRuleEntry updatedUniProtEntry = builder.build();
        assertNotNull(updatedUniProtEntry);
        assertEquals(
                uniRuleEntry.getOtherRules().size() + 2,
                updatedUniProtEntry.getOtherRules().size());
    }

    @Test
    void testCreateObjectUpdateSamFeatureSetsList() {
        UniRuleEntry uniRuleEntry = createObject();
        UniRuleEntryBuilder builder = UniRuleEntryBuilder.from(uniRuleEntry);
        //  add samfeature set
        SamFeatureSet fs1 = SamFeatureSetBuilderTest.createObject();
        builder.samFeatureSetsAdd(fs1);
        UniRuleEntry updatedUniProtEntry = builder.build();
        assertNotNull(updatedUniProtEntry);
        assertEquals(
                uniRuleEntry.getSamFeatureSets().size() + 1,
                updatedUniProtEntry.getSamFeatureSets().size());
    }

    @Test
    void testCreateObjectOverwriteNullSamFeatureSetsList() {
        UniRuleEntry uniRuleEntry = createObject();
        assertFalse(uniRuleEntry.getSamFeatureSets().isEmpty());
        UniRuleEntryBuilder builder = UniRuleEntryBuilder.from(uniRuleEntry);
        //  set null samfeature set
        builder.samFeatureSetsSet(null);
        UniRuleEntry updatedUniProtEntry = builder.build();
        assertNotNull(updatedUniProtEntry);
        assertTrue(updatedUniProtEntry.getSamFeatureSets().isEmpty());
    }

    @Test
    void testCreateObjectUpdatePositionFeatureSetsList() {
        UniRuleEntry uniRuleEntry = createObject();
        UniRuleEntryBuilder builder = UniRuleEntryBuilder.from(uniRuleEntry);
        // add couple of position feature set
        PositionFeatureSet pfs1 = PositionFeatureSetBuilderTest.createObject();
        PositionFeatureSet pfs2 = PositionFeatureSetBuilderTest.createObject();
        builder.positionFeatureSetsAdd(pfs1).positionFeatureSetsAdd(pfs2);
        UniRuleEntry updatedUniProtEntry = builder.build();
        assertNotNull(updatedUniProtEntry);
        assertEquals(
                uniRuleEntry.getPositionFeatureSets().size() + 2,
                updatedUniProtEntry.getPositionFeatureSets().size());
    }

    @Test
    void testCreateObjectUpdateNullValuePositionFeatureSetsList() {
        UniRuleEntry uniRuleEntry = createObject();
        UniRuleEntryBuilder builder = UniRuleEntryBuilder.from(uniRuleEntry);
        // try to add null position feature set. should do nothing
        builder.positionFeatureSetsAdd(null);
        UniRuleEntry updatedUniProtEntry = builder.build();
        assertNotNull(updatedUniProtEntry);
        assertEquals(
                uniRuleEntry.getPositionFeatureSets(),
                updatedUniProtEntry.getPositionFeatureSets());
    }

    @Test
    void testCreateObjectWithOneOtherRule() {
        Information information = InformationBuilderTest.createObject();
        Rule mainRule = RuleBuilderTest.createObject();
        UniRuleEntryBuilder builder =
                new UniRuleEntryBuilder(
                        UniRuleIdBuilderTest.createObject(),
                        RuleStatus.APPLY,
                        information,
                        mainRule);
        CaseRule caseRule = CaseRuleBuilderTest.createObject();
        builder.otherRulesAdd(caseRule);
        UniRuleEntry uniRuleEntry = builder.build();
        assertNotNull(uniRuleEntry);
        assertEquals(Arrays.asList(caseRule), uniRuleEntry.getOtherRules());
    }

    @Test
    void testCreateObjectWithSamFeature() {
        Information information = InformationBuilderTest.createObject();
        Rule mainRule = RuleBuilderTest.createObject();
        UniRuleEntryBuilder builder =
                new UniRuleEntryBuilder(
                        UniRuleIdBuilderTest.createObject(),
                        RuleStatus.APPLY,
                        information,
                        mainRule);
        SamFeatureSet samFeature = SamFeatureSetBuilderTest.createObject();
        builder.samFeatureSetsAdd(samFeature);
        UniRuleEntry uniRuleEntry = builder.build();
        assertNotNull(uniRuleEntry);
        assertEquals(Arrays.asList(samFeature), uniRuleEntry.getSamFeatureSets());
    }

    @Test
    void testCreateObjectWithOnePositionFeature() {
        Information information = InformationBuilderTest.createObject();
        Rule mainRule = RuleBuilderTest.createObject();
        UniRuleEntryBuilder builder =
                new UniRuleEntryBuilder(
                        UniRuleIdBuilderTest.createObject(),
                        RuleStatus.APPLY,
                        information,
                        mainRule);
        PositionFeatureSet positionFeature = PositionFeatureSetBuilderTest.createObject();
        builder.positionFeatureSetsAdd(positionFeature);
        UniRuleEntry uniRuleEntry = builder.build();
        assertNotNull(uniRuleEntry);
        assertEquals(Arrays.asList(positionFeature), uniRuleEntry.getPositionFeatureSets());
    }

    @Test
    void testCreateObjectUpdateMainRule() {
        UniRuleEntry entry = createObject();
        assertNotNull(entry);
        UniRuleEntryBuilder builder = UniRuleEntryBuilder.from(entry);
        // update main rule
        Rule rule = RuleBuilderTest.createObject();
        builder.mainRule(rule);
        UniRuleEntry updatedEntry = builder.build();
        assertNotNull(updatedEntry);
        assertEquals(rule, updatedEntry.getMainRule());
        assertNotSame(entry.getMainRule(), updatedEntry.getMainRule());
    }

    @Test
    void testCreateObjectUpdateInformation() {
        UniRuleEntry entry = createObject();
        assertNotNull(entry);
        UniRuleEntryBuilder builder = UniRuleEntryBuilder.from(entry);
        // update information
        Information info = InformationBuilderTest.createObject();
        builder.information(info);
        UniRuleEntry updatedEntry = builder.build();
        assertNotNull(updatedEntry);
        assertEquals(info, updatedEntry.getInformation());
        assertNotSame(entry.getInformation(), updatedEntry.getInformation());
    }

    public static UniRuleEntry createObject(int listSize, boolean includeEvidences) {
        String random = UUID.randomUUID().toString();
        UniRuleId uniRuleId = UniRuleIdBuilderTest.createObject(listSize);
        Information information = InformationBuilderTest.createObject(listSize);
        int rIndex = ThreadLocalRandom.current().nextInt(0, RuleStatus.values().length);
        RuleStatus ruleStatus = RuleStatus.values()[rIndex];
        Rule mainRule = RuleBuilderTest.createObject(listSize, includeEvidences);
        List<CaseRule> otherRules = CaseRuleBuilderTest.createObjects(listSize, includeEvidences);
        List<SamFeatureSet> samFeatureSets =
                SamFeatureSetBuilderTest.createObjects(listSize, includeEvidences);
        List<PositionFeatureSet> positionFeatureSets =
                PositionFeatureSetBuilderTest.createObjects(listSize, includeEvidences);
        String createdBy = "createdBy" + random;
        String modifiedBy = "modifiedBy" + random;
        LocalDate createdDate = LocalDate.now();
        LocalDate modifiedDate = LocalDate.now();

        UniRuleEntryBuilder builder =
                new UniRuleEntryBuilder(uniRuleId, ruleStatus, information, mainRule);
        builder.otherRulesSet(otherRules).samFeatureSetsSet(samFeatureSets);
        builder.positionFeatureSetsSet(positionFeatureSets);
        builder.createdBy(createdBy).modifiedBy(modifiedBy);
        builder.createdDate(createdDate).modifiedDate(modifiedDate);

        UniRuleEntry uniRuleEntry = builder.build();
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
        return uniRuleEntry;
    }

    public static UniRuleEntry createObject(int listSize) {
        return createObject(listSize, false);
    }

    public static UniRuleEntry createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<UniRuleEntry> createObjects(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count))
                .collect(Collectors.toList());
    }
}
