package org.uniprot.core.unirule.impl;

import org.uniprot.core.unirule.RuleExceptionAnnotation;

import java.util.Objects;

public abstract class RuleExceptionAnnotationImpl implements RuleExceptionAnnotation {
    private static final long serialVersionUID = -5031928797270495795L;
    private RuleExceptionAnnotationType annotationType;

    RuleExceptionAnnotationImpl(RuleExceptionAnnotationType annotationType) {
        this.annotationType = annotationType;
    }

    @Override
    public RuleExceptionAnnotationType getAnnotationType() {
        return annotationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuleExceptionAnnotationImpl ruleExceptionAnnotation = (RuleExceptionAnnotationImpl) o;
        return annotationType == ruleExceptionAnnotation.annotationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotationType);
    }
}
