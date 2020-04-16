package org.uniprot.core.unirule.impl;

import static org.uniprot.core.util.Utils.nullThrowIllegalArgument;

import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.ConditionSet;
import org.uniprot.core.unirule.Rule;
import org.uniprot.core.unirule.RuleException;

public class RuleBuilder<T> extends AbstractRuleBuilder<Rule<T>, T> {

    public @Nonnull RuleBuilder(
            List<ConditionSet> conditionSets,
            List<Annotation> annotations,
            List<RuleException<T>> ruleExceptions) {
        super(conditionSets, annotations, ruleExceptions);
    }

    @Nonnull
    @Override
    public Rule build() {
        return new RuleImpl(conditionSets, annotations, ruleExceptions);
    }

    public static @Nonnull RuleBuilder from(@Nonnull Rule instance) {
        nullThrowIllegalArgument(instance);
        RuleBuilder builder =
                new RuleBuilder(
                        instance.getConditionSets(),
                        instance.getAnnotations(),
                        instance.getRuleExceptions());
        return builder;
    }
}
