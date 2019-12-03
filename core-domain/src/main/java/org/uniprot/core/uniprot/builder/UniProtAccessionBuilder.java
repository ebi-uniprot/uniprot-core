package org.uniprot.core.uniprot.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.builder.AbstractValueBuilder;
import org.uniprot.core.uniprot.UniProtAccession;
import org.uniprot.core.uniprot.impl.UniProtAccessionImpl;

/**
 * Created 24/01/19
 *
 * @author Edd
 */
public class UniProtAccessionBuilder
        extends AbstractValueBuilder<UniProtAccessionBuilder, UniProtAccession> {
    public UniProtAccessionBuilder(String value) {
        super(value);
    }

    @Override
    public @Nonnull UniProtAccession build() {
        return new UniProtAccessionImpl(value);
    }

    @Override
    protected @Nonnull UniProtAccessionBuilder getThis() {
        return this;
    }
}
