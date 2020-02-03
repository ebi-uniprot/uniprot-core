package org.uniprot.core.uniprot.description.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.description.impl.NameImpl;
import org.uniprot.core.uniprot.evidence.builder.AbstractEvidencedValueBuilder;

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
