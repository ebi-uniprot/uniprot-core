package org.uniprot.core.uniref.builder;

import org.uniprot.core.builder.AbstractValueBuilder;
import org.uniprot.core.uniref.UniRefEntryId;
import org.uniprot.core.uniref.impl.UniRefEntryIdImpl;

import javax.annotation.Nonnull;

/**
 * @author jluo
 * @date: 12 Aug 2019
 */
public class UniRefEntryIdBuilder
        extends AbstractValueBuilder<UniRefEntryIdBuilder, UniRefEntryId> {

    public UniRefEntryIdBuilder(String value) {
        super(value);
    }

    @Override
    public @Nonnull UniRefEntryId build() {
        return new UniRefEntryIdImpl(value);
    }

    @Override
    protected @Nonnull UniRefEntryIdBuilder getThis() {
        return this;
    }
}
