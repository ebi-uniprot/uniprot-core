package org.uniprot.core.unirule.impl;

import static org.uniprot.core.util.Utils.nullThrowIllegalArgument;

import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.unirule.RuleExceptionAnnotationType;
import org.uniprot.core.unirule.ConditionSet;
import org.uniprot.core.unirule.Rule;

public class RuleBuilder<R extends RuleExceptionAnnotationType> extends AbstractRuleBuilder<RuleBuilder<R>, Rule<R>, R> {

    public RuleBuilder(ConditionSet conditionSet) {
        super(conditionSet);
    }

    public RuleBuilder(List<ConditionSet> conditionSets) {
        super(conditionSets);
    }

    @Nonnull
    @Override
    public Rule<R> build() {
        return new RuleImpl(conditionSets, annotations, ruleExceptions);
    }

    public static @Nonnull RuleBuilder from(@Nonnull Rule instance) {
        nullThrowIllegalArgument(instance);
        RuleBuilder builder = new RuleBuilder(instance.getConditionSets());
        builder.annotationsSet(instance.getAnnotations());
        builder.ruleExceptionsSet(instance.getRuleExceptions());
        return builder;
    }

    @Override
    protected @Nonnull RuleBuilder<R> getThis() {
        return this;
    }
}
