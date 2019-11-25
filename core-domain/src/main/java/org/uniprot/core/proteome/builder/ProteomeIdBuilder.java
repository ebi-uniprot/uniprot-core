package org.uniprot.core.proteome.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.builder.AbstractValueBuilder;
import org.uniprot.core.proteome.ProteomeId;
import org.uniprot.core.proteome.impl.ProteomeIdImpl;

public class ProteomeIdBuilder extends AbstractValueBuilder<ProteomeIdBuilder, ProteomeId> {
    public ProteomeIdBuilder(String value) {
        super(value);
    }

    @Override
    public @Nonnull ProteomeId build() {
        return new ProteomeIdImpl(value);
    }

    @Override
    protected ProteomeIdBuilder getThis() {
        return this;
    }
}
