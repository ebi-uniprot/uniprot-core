package org.uniprot.core.unirule.impl;

import static org.uniprot.core.util.Utils.*;

import java.util.ArrayList;
import java.util.Date;
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
    private String createdBy;
    private String modifiedBy;
    private Date createdDate;
    private Date modifiedDate;

    public UniRuleEntryBuilder(UniRuleId uniRuleId, RuleStatus ruleStatus) {
        this.uniRuleId = uniRuleId;
        this.ruleStatus = ruleStatus;
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

    public @Nonnull UniRuleEntryBuilder createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public @Nonnull UniRuleEntryBuilder modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public @Nonnull UniRuleEntryBuilder createdDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public @Nonnull UniRuleEntryBuilder modifiedDate(Date modifiedDate) {
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
                createdBy,
                modifiedBy,
                createdDate,
                modifiedDate);
    }

    public static @Nonnull UniRuleEntryBuilder from(@Nonnull UniRuleEntry instance) {
        nullThrowIllegalArgument(instance);
        UniRuleEntryBuilder builder =
                new UniRuleEntryBuilder(instance.getUniRuleId(), instance.getRuleStatus());
        builder.information(instance.getInformation())
                .mainRule(instance.getMainRule())
                .otherRulesSet(instance.getOtherRules())
                .samFeatureSetsSet(instance.getSamFeatureSets())
                .positionFeatureSetsSet(instance.getPositionFeatureSets())
                .createdBy(instance.getCreatedBy())
                .modifiedBy(instance.getModifiedBy())
                .createdDate(instance.getCreatedDate())
                .modifiedDate(instance.getModifiedDate());
        return builder;
    }
}
