package org.uniprot.core.unirule.impl;

import static org.uniprot.core.util.Utils.nullThrowIllegalArgument;

import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.unirule.RuleExceptionAnnotationType;
import org.uniprot.core.unirule.CaseRule;
import org.uniprot.core.unirule.ConditionSet;

public class CaseRuleBuilder<R extends RuleExceptionAnnotationType> extends AbstractRuleBuilder<CaseRuleBuilder<R>, CaseRule<R>, R> {
    private boolean overallStatsExempted;

    public CaseRuleBuilder(ConditionSet conditionSet) {
        super(conditionSet);
    }

    public CaseRuleBuilder(List<ConditionSet> conditionSets) {
        super(conditionSets);
    }

    public @Nonnull CaseRuleBuilder<R> overallStatsExempted(boolean overallStatsExempted) {
        this.overallStatsExempted = overallStatsExempted;
        return this;
    }

    @Nonnull
    @Override
    public CaseRule<R> build() {
        return new CaseRuleImpl(conditionSets, annotations, ruleExceptions, overallStatsExempted);
    }

    public static @Nonnull <R extends RuleExceptionAnnotationType> CaseRuleBuilder from(@Nonnull CaseRule<R> instance) {
        nullThrowIllegalArgument(instance);
        CaseRuleBuilder<R> builder = new CaseRuleBuilder(instance.getConditionSets());
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
