package org.uniprot.core.proteome.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.impl.AbstractValueBuilder;
import org.uniprot.core.proteome.ProteomeId;

public class ProteomeIdBuilder extends AbstractValueBuilder<ProteomeId> {
    public ProteomeIdBuilder(String value) {
        super(value);
    }

    @Override
    public @Nonnull ProteomeId build() {
        return new ProteomeIdImpl(value);
    }

    public static @Nonnull ProteomeIdBuilder from(@Nonnull ProteomeId instance) {
        return new ProteomeIdBuilder(instance.getValue());
    }
}
