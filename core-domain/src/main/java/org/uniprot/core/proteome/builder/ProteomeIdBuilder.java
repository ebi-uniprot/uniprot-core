package org.uniprot.core.proteome.builder;

import org.uniprot.core.builder.AbstractValueBuilder;
import org.uniprot.core.proteome.ProteomeId;
import org.uniprot.core.proteome.impl.ProteomeIdImpl;

import javax.annotation.Nonnull;

public class ProteomeIdBuilder extends AbstractValueBuilder<ProteomeIdBuilder, ProteomeId> {
    public ProteomeIdBuilder(String value) {
        super(value);
    }

    @Override
    public @Nonnull
    ProteomeId build() {
        return new ProteomeIdImpl(value);
    }

    @Override
    protected ProteomeIdBuilder getThis() {
        return this;
    }
}
