package org.uniprot.core.unirule.builder;

import static org.uniprot.core.util.Utils.nullThrowIllegalArgument;

import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.unirule.*;
import org.uniprot.core.unirule.impl.CaseRuleImpl;

public class CaseRuleBuilder extends AbstractRuleBuilder<CaseRule> {
    private boolean overallStatsExempted;

    public CaseRuleBuilder(
            List<ConditionSet> conditionSets,
            List<Annotation> annotations,
            List<RuleException> ruleExceptions) {
        super(conditionSets, annotations, ruleExceptions);
    }

    public CaseRuleBuilder overallStatsExempted(boolean overallStatsExempted) {
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
        CaseRuleBuilder builder =
                new CaseRuleBuilder(
                        instance.getConditionSets(),
                        instance.getAnnotations(),
                        instance.getRuleExceptions());
        builder.overallStatsExempted(instance.isOverallStatsExempted());
        return builder;
    }
}
