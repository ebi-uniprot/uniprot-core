package org.uniprot.core.uniref.impl;

import org.uniprot.core.impl.AbstractValueBuilder;
import org.uniprot.core.uniref.UniRefEntryId;

import javax.annotation.Nonnull;

/**
 * @author jluo
 * @date: 12 Aug 2019
 */
public class UniRefEntryIdBuilder extends AbstractValueBuilder<UniRefEntryId> {

    public UniRefEntryIdBuilder(String value) {
        super(value);
    }

    @Override
    public @Nonnull UniRefEntryId build() {
        return new UniRefEntryIdImpl(value);
    }

    public static @Nonnull UniRefEntryIdBuilder from(@Nonnull UniRefEntryId instance) {
        return new UniRefEntryIdBuilder(instance.getValue());
    }
}
