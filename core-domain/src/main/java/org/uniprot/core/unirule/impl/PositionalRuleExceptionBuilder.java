package org.uniprot.core.unirule.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.unirule.PositionalFeature;
import org.uniprot.core.unirule.RuleException;

public class PositionalRuleExceptionBuilder
        extends AbstractRuleExceptionBuilder<
                PositionalRuleExceptionBuilder, PositionalRuleExceptionImpl, PositionalFeature> {

    public PositionalRuleExceptionBuilder(String category) {
        super(category);
    }

    @Nonnull
    @Override
    public PositionalRuleExceptionImpl build() {
        return new PositionalRuleExceptionImpl(note, category, annotation, accessions);
    }

    public static @Nonnull PositionalRuleExceptionBuilder from(
            @Nonnull RuleException<PositionalFeature> instance) {
        PositionalRuleExceptionBuilder builder =
                new PositionalRuleExceptionBuilder(instance.getCategory());
        builder.annotation(instance.getAnnotation())
                .note(instance.getNote())
                .accessionsSet(instance.getAccessions());
        return builder;
    }

    @Nonnull
    @Override
    protected PositionalRuleExceptionBuilder getThis() {
        return this;
    }
}
