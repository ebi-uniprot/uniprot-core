package org.uniprot.core.uniprot.builder;

import org.uniprot.core.builder.AbstractValueBuilder;
import org.uniprot.core.uniprot.UniProtId;
import org.uniprot.core.uniprot.impl.UniProtIdImpl;

import javax.annotation.Nonnull;

/**
 * Created 24/01/19
 *
 * @author Edd
 */
public class UniProtIdBuilder extends AbstractValueBuilder<UniProtIdBuilder, UniProtId> {
    public UniProtIdBuilder(String value) {
        super(value);
    }

    @Override
    public @Nonnull UniProtId build() {
        return new UniProtIdImpl(value);
    }

    @Override
    protected UniProtIdBuilder getThis() {
        return this;
    }
}
