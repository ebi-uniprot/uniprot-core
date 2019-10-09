package org.uniprot.core.uniprot.description.builder;

import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.description.impl.NameImpl;
import org.uniprot.core.uniprot.evidence.builder.AbstractEvidencedValueBuilder;

import javax.annotation.Nonnull;

public class NameBuilder extends AbstractEvidencedValueBuilder<NameBuilder, Name> {

    @Override
    protected NameBuilder getThis() {
        return this;
    }

    @Override
    public @Nonnull
    Name build() {
        return new NameImpl(value, evidences);
    }
}
