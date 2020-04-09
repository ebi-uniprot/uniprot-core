package org.uniprot.core.unirule.builder;

import static org.uniprot.core.util.Utils.*;

import java.util.Date;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.unirule.*;
import org.uniprot.core.unirule.impl.UniRuleEntryImpl;

public class UniRuleEntryBuilder implements Builder<UniRuleEntry> {
    private UniRuleId uniRuleId;
    private Information information;
    private RuleStatus ruleStatus;
    private Rule mainRule;
    private List<CaseRule> otherRules;
    private List<SamFeatureSet> samFeatureSets;
    private List<PositionFeatureSet> positionFeatureSets;
    private String createdBy;
    private String modifiedBy;
    private Date createdDate;
    private Date modifiedDate;

    public UniRuleEntryBuilder uniRuleId(UniRuleId uniRuleId) {
        this.uniRuleId = uniRuleId;
        return this;
    }

    public UniRuleEntryBuilder information(Information information) {
        this.information = information;
        return this;
    }

    public UniRuleEntryBuilder ruleStatus(RuleStatus ruleStatus) {
        this.ruleStatus = ruleStatus;
        return this;
    }

    public UniRuleEntryBuilder mainRule(Rule mainRule) {
        this.mainRule = mainRule;
        return this;
    }

    public UniRuleEntryBuilder otherRulesAdd(CaseRule otherRule) {
        addOrIgnoreNull(otherRule, this.otherRules);
        return this;
    }

    public UniRuleEntryBuilder otherRulesSet(List<CaseRule> otherRules) {
        this.otherRules = modifiableList(otherRules);
        return this;
    }

    public UniRuleEntryBuilder samFeatureSetsAdd(SamFeatureSet samFeatureSet) {
        addOrIgnoreNull(samFeatureSet, this.samFeatureSets);
        return this;
    }

    public UniRuleEntryBuilder samFeatureSetsSet(List<SamFeatureSet> samFeatureSets) {
        this.samFeatureSets = modifiableList(samFeatureSets);
        return this;
    }

    public UniRuleEntryBuilder positionFeatureSetsAdd(PositionFeatureSet positionFeatureSet) {
        addOrIgnoreNull(positionFeatureSet, this.positionFeatureSets);
        return this;
    }

    public UniRuleEntryBuilder positionFeatureSetsSet(
            List<PositionFeatureSet> positionFeatureSets) {
        this.positionFeatureSets = modifiableList(positionFeatureSets);
        return this;
    }

    public UniRuleEntryBuilder createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public UniRuleEntryBuilder modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public UniRuleEntryBuilder createdDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public UniRuleEntryBuilder modifiedDate(Date modifiedDate) {
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
        UniRuleEntryBuilder builder = new UniRuleEntryBuilder();
        builder.uniRuleId(instance.getUniRuleId())
                .information(instance.getInformation())
                .ruleStatus(instance.getRuleStatus())
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
