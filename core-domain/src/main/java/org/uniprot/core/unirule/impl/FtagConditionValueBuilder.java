package org.uniprot.core.unirule.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.impl.AbstractValueBuilder;
import org.uniprot.core.unirule.FtagConditionValue;

public class FtagConditionValueBuilder extends AbstractValueBuilder<FtagConditionValue> {

    private String pattern;

    public @Nonnull FtagConditionValueBuilder(String value) {
        super(value);
    }

    public @Nonnull FtagConditionValueBuilder pattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    @Nonnull
    @Override
    public FtagConditionValue build() {
        return new FtagConditionValueImpl(value, pattern);
    }

    public static @Nonnull FtagConditionValueBuilder from(@Nonnull FtagConditionValue instance) {
        FtagConditionValueBuilder builder = new FtagConditionValueBuilder(instance.getValue());
        builder.pattern(instance.getPattern());
        return builder;
    }
}
