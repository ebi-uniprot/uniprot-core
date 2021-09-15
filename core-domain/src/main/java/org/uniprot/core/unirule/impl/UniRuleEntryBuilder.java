package org.uniprot.core.unirule.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.Statistics;
import org.uniprot.core.unirule.CaseRule;
import org.uniprot.core.unirule.Information;
import org.uniprot.core.unirule.PositionFeatureSet;
import org.uniprot.core.unirule.Rule;
import org.uniprot.core.unirule.SamFeatureSet;
import org.uniprot.core.unirule.UniRuleEntry;
import org.uniprot.core.unirule.UniRuleId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;
import static org.uniprot.core.util.Utils.nullThrowIllegalArgument;

public class UniRuleEntryBuilder implements Builder<UniRuleEntry> {
    private UniRuleId uniRuleId;
    private Information information;
    private Rule mainRule;
    private List<CaseRule> otherRules = new ArrayList<>();
    private List<SamFeatureSet> samFeatureSets = new ArrayList<>();
    private List<PositionFeatureSet> positionFeatureSets = new ArrayList<>();
    private Statistics statistics;
    private LocalDate createdDate;
    private LocalDate modifiedDate;

    public UniRuleEntryBuilder(
            UniRuleId uniRuleId, Information information, Rule mainRule) {
        this.uniRuleId = uniRuleId;
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

    public @Nonnull UniRuleEntryBuilder statistics(Statistics statistics) {
        this.statistics = statistics;
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
                mainRule,
                otherRules,
                samFeatureSets,
                positionFeatureSets,
                statistics,
                createdDate,
                modifiedDate);
    }

    public static @Nonnull UniRuleEntryBuilder from(@Nonnull UniRuleEntry instance) {
        nullThrowIllegalArgument(instance);
        UniRuleId uniRuleId = instance.getUniRuleId();
        Information information = instance.getInformation();
        Rule mainRule = instance.getMainRule();

        UniRuleEntryBuilder builder =
                new UniRuleEntryBuilder(uniRuleId, information, mainRule);

        builder.otherRulesSet(instance.getOtherRules())
                .samFeatureSetsSet(instance.getSamFeatureSets())
                .positionFeatureSetsSet(instance.getPositionFeatureSets())
                .statistics(instance.getStatistics())
                .createdDate(instance.getCreatedDate())
                .modifiedDate(instance.getModifiedDate());
        return builder;
    }
}
