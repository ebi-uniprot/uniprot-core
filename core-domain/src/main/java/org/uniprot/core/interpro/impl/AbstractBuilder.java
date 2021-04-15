package org.uniprot.core.interpro.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.impl.AbstractValueBuilder;
import org.uniprot.core.interpro.Abstract;

/**
 * @author jluo
 * @date: 12 Apr 2021
 */
public class AbstractBuilder extends AbstractValueBuilder<Abstract> {
    public AbstractBuilder(String value) {
        super(value);
    }

    @Override
    public @Nonnull Abstract build() {
        return new AbstractImpl(value);
    }

    public static @Nonnull AbstractBuilder from(@Nonnull Abstract instance) {
        return new AbstractBuilder(instance.getValue());
    }
}
