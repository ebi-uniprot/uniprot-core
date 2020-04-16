package org.uniprot.core.unirule.impl;

import static org.uniprot.core.util.Utils.nullThrowIllegalArgument;

import javax.annotation.Nonnull;

import org.uniprot.core.unirule.CaseRule;

public class CaseRuleBuilder<R> extends AbstractRuleBuilder<CaseRuleBuilder<R>, CaseRule<R>, R> {
    private boolean overallStatsExempted;

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
        CaseRuleBuilder builder = new CaseRuleBuilder();
        builder.conditionSetsSet(instance.getConditionSets());
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
