package org.uniprot.core.unirule.impl;

import static org.uniprot.core.util.Utils.nullThrowIllegalArgument;

import javax.annotation.Nonnull;

import org.uniprot.core.unirule.Rule;

public class RuleBuilder<R> extends AbstractRuleBuilder<RuleBuilder<R>, Rule<R>, R> {
    @Nonnull
    @Override
    public Rule build() {
        return new RuleImpl(conditionSets, annotations, ruleExceptions);
    }

    public static @Nonnull RuleBuilder from(@Nonnull Rule instance) {
        nullThrowIllegalArgument(instance);
        RuleBuilder builder = new RuleBuilder();
        builder.conditionSetsSet(instance.getConditionSets());
        builder.annotationsSet(instance.getAnnotations());
        builder.ruleExceptionsSet(instance.getRuleExceptions());
        return builder;
    }

    @Override
    protected @Nonnull RuleBuilder<R> getThis() {
        return this;
    }
}
