package org.uniprot.core.uniprotkb.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.impl.AbstractValueBuilder;
import org.uniprot.core.uniprotkb.UniProtKBId;

/**
 * Created 24/01/19
 *
 * @author Edd
 */
public class UniProtKBIdBuilder extends AbstractValueBuilder<UniProtKBId> {
    public UniProtKBIdBuilder(String value) {
        super(value);
    }

    @Override
    public @Nonnull UniProtKBId build() {
        return new UniProtKBIdImpl(value);
    }

    public static @Nonnull UniProtKBIdBuilder from(@Nonnull UniProtKBId instance) {
        return new UniProtKBIdBuilder(instance.getValue());
    }
}
