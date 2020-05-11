package org.uniprot.core.uniprotkb.description.impl;

import org.uniprot.core.uniprotkb.description.Name;
import org.uniprot.core.uniprotkb.evidence.impl.AbstractEvidencedValueBuilder;

import javax.annotation.Nonnull;

public class NameBuilder extends AbstractEvidencedValueBuilder<NameBuilder, Name> {

    @Override
    protected @Nonnull NameBuilder getThis() {
        return this;
    }

    @Override
    public @Nonnull Name build() {
        return new NameImpl(value, evidences);
    }

    public static @Nonnull NameBuilder from(@Nonnull Name instance) {
        NameBuilder builder = new NameBuilder();
        AbstractEvidencedValueBuilder.init(builder, instance);
        return builder;
    }
}
