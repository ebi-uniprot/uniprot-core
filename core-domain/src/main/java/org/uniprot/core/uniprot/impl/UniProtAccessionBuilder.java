package org.uniprot.core.uniprot.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.impl.AbstractValueBuilder;
import org.uniprot.core.uniprot.UniProtAccession;

/**
 * Created 24/01/19
 *
 * @author Edd
 */
public class UniProtAccessionBuilder extends AbstractValueBuilder<UniProtAccession> {
    public UniProtAccessionBuilder(String value) {
        super(value);
    }

    @Override
    public @Nonnull UniProtAccession build() {
        return new UniProtAccessionImpl(value);
    }

    public static @Nonnull UniProtAccessionBuilder from(@Nonnull UniProtAccession instance) {
        return new UniProtAccessionBuilder(instance.getValue());
    }
}
