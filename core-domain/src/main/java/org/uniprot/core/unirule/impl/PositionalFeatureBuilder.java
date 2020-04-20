package org.uniprot.core.unirule.impl;

import static org.uniprot.core.util.Utils.nullThrowIllegalArgument;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.Range;
import org.uniprot.core.unirule.PositionalFeature;

public class PositionalFeatureBuilder implements Builder<PositionalFeature> {
    private Range position;

    private String pattern;

    private boolean inGroup;

    private String value;

    private String type;

    public @Nonnull PositionalFeatureBuilder position(Range position) {
        this.position = position;
        return this;
    }

    public @Nonnull PositionalFeatureBuilder pattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public @Nonnull PositionalFeatureBuilder inGroup(boolean inGroup) {
        this.inGroup = inGroup;
        return this;
    }

    public @Nonnull PositionalFeatureBuilder value(String value) {
        this.value = value;
        return this;
    }

    public @Nonnull PositionalFeatureBuilder type(String type) {
        this.type = type;
        return this;
    }

    @Nonnull
    @Override
    public PositionalFeature build() {
        return new PositionalFeatureImpl(position, pattern, inGroup, value, type);
    }

    public static @Nonnull PositionalFeatureBuilder from(@Nonnull PositionalFeature instance) {
        nullThrowIllegalArgument(instance);
        PositionalFeatureBuilder builder = new PositionalFeatureBuilder();
        builder.position(instance.getPosition())
                .pattern(instance.getPattern())
                .inGroup(instance.isInGroup())
                .value(instance.getValue())
                .type(instance.getType());
        return builder;
    }
}
