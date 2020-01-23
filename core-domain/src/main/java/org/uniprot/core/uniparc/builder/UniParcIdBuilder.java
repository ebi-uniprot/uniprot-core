package org.uniprot.core.uniparc.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.builder.AbstractValueBuilder;
import org.uniprot.core.uniparc.UniParcId;
import org.uniprot.core.uniparc.impl.UniParcIdImpl;

/**
 * @author jluo
 * @date: 23 May 2019
 */
public class UniParcIdBuilder extends AbstractValueBuilder<UniParcIdBuilder, UniParcId> {
    public UniParcIdBuilder(String value) {
        super(value);
    }

    @Override
    public @Nonnull UniParcId build() {
        return new UniParcIdImpl(value);
    }

    public static @Nonnull UniParcIdBuilder from(@Nonnull UniParcId instance) {
        return new UniParcIdBuilder(instance.getValue());
    }
}
