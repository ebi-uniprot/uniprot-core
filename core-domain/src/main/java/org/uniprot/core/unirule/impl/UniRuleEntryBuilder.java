package org.uniprot.core.unirule.impl;

import static org.uniprot.core.util.Utils.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.unirule.*;

public class UniRuleEntryBuilder implements Builder<UniRuleEntry> {
    private UniRuleId uniRuleId;
    private Information information;
    private RuleStatus ruleStatus;
    private Rule mainRule;
    private List<CaseRule> otherRules = new ArrayList<>();
    private List<SamFeatureSet> samFeatureSets = new ArrayList<>();
    private List<PositionFeatureSet> positionFeatureSets = new ArrayList<>();
    private Long proteinsAnnotatedCount;
    private String createdBy;
    private String modifiedBy;
    private LocalDate createdDate;
    private LocalDate modifiedDate;

    public UniRuleEntryBuilder(
            UniRuleId uniRuleId, RuleStatus ruleStatus, Information information, Rule mainRule) {
        this.uniRuleId = uniRuleId;
        this.ruleStatus = ruleStatus;
        this.information = information;
        this.mainRule = mainRule;
    }

    public @Nonnull UniRuleEntryBuilder uniRuleId(UniRuleId uniRuleId) {
        this.uniRuleId = uniRuleId;
        return this;
    }

    public @Nonnull UniRuleEntryBuilder information(Information information) {
        this.information = information;
        return this;
    }

    public @Nonnull UniRuleEntryBuilder ruleStatus(RuleStatus ruleStatus) {
        this.ruleStatus = ruleStatus;
        return this;
    }

    public @Nonnull UniRuleEntryBuilder mainRule(Rule mainRule) {
        this.mainRule = mainRule;
        return this;
    }

    public @Nonnull UniRuleEntryBuilder otherRulesAdd(CaseRule otherRule) {
        addOrIgnoreNull(otherRule, this.otherRules);
        return this;
    }

    public @Nonnull UniRuleEntryBuilder otherRulesSet(List<CaseRule> otherRules) {
        this.otherRules = modifiableList(otherRules);
        return this;
    }

    public @Nonnull UniRuleEntryBuilder samFeatureSetsAdd(SamFeatureSet samFeatureSet) {
        addOrIgnoreNull(samFeatureSet, this.samFeatureSets);
        return this;
    }

    public @Nonnull UniRuleEntryBuilder samFeatureSetsSet(List<SamFeatureSet> samFeatureSets) {
        this.samFeatureSets = modifiableList(samFeatureSets);
        return this;
    }

    public @Nonnull UniRuleEntryBuilder positionFeatureSetsAdd(
            PositionFeatureSet positionFeatureSet) {
        addOrIgnoreNull(positionFeatureSet, this.positionFeatureSets);
        return this;
    }

    public @Nonnull UniRuleEntryBuilder positionFeatureSetsSet(
            List<PositionFeatureSet> positionFeatureSets) {
        this.positionFeatureSets = modifiableList(positionFeatureSets);
        return this;
    }

    public @Nonnull UniRuleEntryBuilder proteinsAnnotatedCount(Long proteinsAnnotatedCount) {
        this.proteinsAnnotatedCount = proteinsAnnotatedCount;
        return this;
    }

    public @Nonnull UniRuleEntryBuilder createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public @Nonnull UniRuleEntryBuilder modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public @Nonnull UniRuleEntryBuilder createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public @Nonnull UniRuleEntryBuilder modifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    @Nonnull
    @Override
    public UniRuleEntry build() {
        return new UniRuleEntryImpl(
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
    }

    public static @Nonnull UniRuleEntryBuilder from(@Nonnull UniRuleEntry instance) {
        nullThrowIllegalArgument(instance);
        UniRuleId uniRuleId = instance.getUniRuleId();
        RuleStatus ruleStatus = instance.getRuleStatus();
        Information information = instance.getInformation();
        Rule mainRule = instance.getMainRule();

        UniRuleEntryBuilder builder =
                new UniRuleEntryBuilder(uniRuleId, ruleStatus, information, mainRule);

        builder.otherRulesSet(instance.getOtherRules())
                .samFeatureSetsSet(instance.getSamFeatureSets())
                .positionFeatureSetsSet(instance.getPositionFeatureSets())
                .proteinsAnnotatedCount(instance.getProteinsAnnotatedCount())
                .createdBy(instance.getCreatedBy())
                .modifiedBy(instance.getModifiedBy())
                .createdDate(instance.getCreatedDate())
                .modifiedDate(instance.getModifiedDate());
        return builder;
    }
}
