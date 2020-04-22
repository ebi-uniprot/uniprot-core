package org.uniprot.core.unirule.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.RuleException;

public class AnnotationRuleExceptionBuilder
        extends AbstractRuleExceptionBuilder<
                AnnotationRuleExceptionBuilder, AnnotationRuleExceptionImpl, Annotation> {

    public AnnotationRuleExceptionBuilder(String category) {
        super(category);
    }

    @Nonnull
    @Override
    public AnnotationRuleExceptionImpl build() {
        return new AnnotationRuleExceptionImpl(note, category, annotation, accessions);
    }

    public static @Nonnull AnnotationRuleExceptionBuilder from(
            @Nonnull RuleException<Annotation> instance) {
        AnnotationRuleExceptionBuilder builder =
                new AnnotationRuleExceptionBuilder(instance.getCategory());
        builder.annotation(instance.getAnnotation())
                .note(instance.getNote())
                .accessionsSet(instance.getAccessions());
        return builder;
    }

    @Nonnull
    @Override
    protected AnnotationRuleExceptionBuilder getThis() {
        return this;
    }
}
