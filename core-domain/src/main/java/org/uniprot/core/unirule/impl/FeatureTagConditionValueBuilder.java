package org.uniprot.core.unirule.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.impl.AbstractValueBuilder;
import org.uniprot.core.unirule.FeatureTagConditionValue;

public class FeatureTagConditionValueBuilder
        extends AbstractValueBuilder<FeatureTagConditionValue> {

    private String pattern;

    public @Nonnull FeatureTagConditionValueBuilder(String value) {
        super(value);
    }

    public @Nonnull FeatureTagConditionValueBuilder pattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    @Nonnull
    @Override
    public FeatureTagConditionValue build() {
        return new FeatureTagConditionValueImpl(value, pattern);
    }

    public static @Nonnull FeatureTagConditionValueBuilder from(
            @Nonnull FeatureTagConditionValue instance) {
        FeatureTagConditionValueBuilder builder =
                new FeatureTagConditionValueBuilder(instance.getValue());
        builder.pattern(instance.getPattern());
        return builder;
    }
}
