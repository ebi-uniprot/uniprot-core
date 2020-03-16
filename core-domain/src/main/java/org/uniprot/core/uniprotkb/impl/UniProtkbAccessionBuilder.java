package org.uniprot.core.uniprotkb.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.impl.AbstractValueBuilder;
import org.uniprot.core.uniprotkb.UniProtkbAccession;

/**
 * Created 24/01/19
 *
 * @author Edd
 */
public class UniProtkbAccessionBuilder extends AbstractValueBuilder<UniProtkbAccession> {
    public UniProtkbAccessionBuilder(String value) {
        super(value);
    }

    @Override
    public @Nonnull UniProtkbAccession build() {
        return new UniProtkbAccessionImpl(value);
    }

    public static @Nonnull UniProtkbAccessionBuilder from(@Nonnull UniProtkbAccession instance) {
        return new UniProtkbAccessionBuilder(instance.getValue());
    }
}
