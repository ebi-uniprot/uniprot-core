package org.uniprot.core.unirule.builder;

import static org.uniprot.core.util.Utils.nullThrowIllegalArgument;

import javax.annotation.Nonnull;

import org.uniprot.core.impl.AbstractValueBuilder;
import org.uniprot.core.unirule.UniRuleId;
import org.uniprot.core.unirule.impl.UniRuleIdImpl;

public class UniRuleIdBuilder extends AbstractValueBuilder<UniRuleId> {

    public UniRuleIdBuilder(String value) {
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
