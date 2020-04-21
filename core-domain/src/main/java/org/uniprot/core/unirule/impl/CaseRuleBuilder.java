package org.uniprot.core.unirule.impl;

import static org.uniprot.core.util.Utils.nullThrowIllegalArgument;

import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.unirule.CaseRule;
import org.uniprot.core.unirule.ConditionSet;

public class CaseRuleBuilder<R> extends AbstractRuleBuilder<CaseRuleBuilder<R>, CaseRule<R>, R> {
    private boolean overallStatsExempted;

    public CaseRuleBuilder(ConditionSet conditionSet) {
        super(conditionSet);
    }

    public CaseRuleBuilder(List<ConditionSet> conditionSets) {
        super(conditionSets);
    }

    public @Nonnull CaseRuleBuilder overallStatsExempted(boolean overallStatsExempted) {
        this.overallStatsExempted = overallStatsExempted;
        return this;
    }

    @Nonnull
    @Override
    public CaseRule build() {
        return new CaseRuleImpl(conditionSets, annotations, ruleExceptions, overallStatsExempted);
    }

    public static @Nonnull CaseRuleBuilder from(@Nonnull CaseRule instance) {
        nullThrowIllegalArgument(instance);
        CaseRuleBuilder builder = new CaseRuleBuilder(instance.getConditionSets());
        builder.annotationsSet(instance.getAnnotations());
        builder.ruleExceptionsSet(instance.getRuleExceptions());
        builder.overallStatsExempted(instance.isOverallStatsExempted());
        return builder;
    }

    @Nonnull
    @Override
    protected CaseRuleBuilder<R> getThis() {
        return this;
    }
}
