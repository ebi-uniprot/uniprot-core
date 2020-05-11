package org.uniprot.core.unirule.impl;

import static org.uniprot.core.util.Utils.nullThrowIllegalArgument;

import org.uniprot.core.impl.AbstractValueBuilder;
import org.uniprot.core.unirule.UniRuleId;

import javax.annotation.Nonnull;

public class UniRuleIdBuilder extends AbstractValueBuilder<UniRuleId> {

    public @Nonnull UniRuleIdBuilder(String value) {
        super(value);
    }

    @Nonnull
    @Override
    public UniRuleId build() {
        return new UniRuleIdImpl(value);
    }

    public static @Nonnull UniRuleIdBuilder from(@Nonnull UniRuleId instance) {
        nullThrowIllegalArgument(instance);
        return new UniRuleIdBuilder(instance.getValue());
    }
}
