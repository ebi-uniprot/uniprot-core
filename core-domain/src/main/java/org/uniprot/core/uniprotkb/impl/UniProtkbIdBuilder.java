package org.uniprot.core.uniprotkb.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.impl.AbstractValueBuilder;
import org.uniprot.core.uniprotkb.UniProtkbId;

/**
 * Created 24/01/19
 *
 * @author Edd
 */
public class UniProtkbIdBuilder extends AbstractValueBuilder<UniProtkbId> {
    public UniProtkbIdBuilder(String value) {
        super(value);
    }

    @Override
    public @Nonnull UniProtkbId build() {
        return new UniProtkbIdImpl(value);
    }

    public static @Nonnull UniProtkbIdBuilder from(@Nonnull UniProtkbId instance) {
        return new UniProtkbIdBuilder(instance.getValue());
    }
}
