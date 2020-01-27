package org.uniprot.core.uniprot.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.builder.AbstractValueBuilder;
import org.uniprot.core.uniprot.UniProtId;
import org.uniprot.core.uniprot.impl.UniProtIdImpl;

/**
 * Created 24/01/19
 *
 * @author Edd
 */
public class UniProtIdBuilder extends AbstractValueBuilder<UniProtId> {
    public UniProtIdBuilder(String value) {
        super(value);
    }

    @Override
    public @Nonnull UniProtId build() {
        return new UniProtIdImpl(value);
    }

    public static @Nonnull UniProtIdBuilder from(@Nonnull UniProtId instance) {
        return new UniProtIdBuilder(instance.getValue());
    }
}
