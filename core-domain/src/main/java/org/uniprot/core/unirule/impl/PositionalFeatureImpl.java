package org.uniprot.core.unirule.impl;

import org.uniprot.core.Range;
import org.uniprot.core.unirule.PositionalFeature;

import java.util.Objects;

public class PositionalFeatureImpl extends RuleExceptionAnnotationImpl
        implements PositionalFeature {

    private static final long serialVersionUID = -7323690314197824278L;
    private Range position;

    private String pattern;

    private boolean inGroup;

    private String value;

    private String type;

    PositionalFeatureImpl() {
        super(RuleExceptionAnnotationType.POSITIONAL_FEATURE);
    }

    PositionalFeatureImpl(
            Range position, String pattern, boolean inGroup, String value, String type) {
        super(RuleExceptionAnnotationType.POSITIONAL_FEATURE);

        if (Objects.isNull(position)) {
            throw new IllegalArgumentException(
                    "position is mandatory parameter for a PositionalFeature entry");
        }

        this.position = position;
        this.pattern = pattern;
        this.inGroup = inGroup;
        this.value = value;
        this.type = type;
    }

    @Override
    public Range getPosition() {
        return position;
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public boolean isInGroup() {
        return inGroup;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PositionalFeatureImpl)) return false;
        if (!super.equals(o)) return false;
        PositionalFeatureImpl that = (PositionalFeatureImpl) o;
        return inGroup == that.inGroup
                && Objects.equals(position, that.position)
                && Objects.equals(pattern, that.pattern)
                && Objects.equals(value, that.value)
                && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), position, pattern, inGroup, value, type);
    }
}
