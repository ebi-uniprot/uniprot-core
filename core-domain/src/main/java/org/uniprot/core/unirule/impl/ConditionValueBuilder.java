package org.uniprot.core.unirule.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.impl.AbstractValueBuilder;
import org.uniprot.core.unirule.ConditionValue;

public class ConditionValueBuilder extends AbstractValueBuilder<ConditionValue> {

    private String cvId;

    public @Nonnull ConditionValueBuilder(String value) {
        super(value);
    }

    public @Nonnull ConditionValueBuilder cvId(String cvId) {
        this.cvId = cvId;
        return this;
    }

    @Nonnull
    @Override
    public ConditionValue build() {
        return new ConditionValueImpl(value, cvId);
    }

    public static @Nonnull ConditionValueBuilder from(@Nonnull ConditionValue instance) {
        ConditionValueBuilder builder = new ConditionValueBuilder(instance.getValue());
        builder.cvId(instance.getCvId());
        return builder;
    }
}
