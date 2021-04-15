package org.uniprot.core.interpro.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.impl.AbstractValueBuilder;
import org.uniprot.core.interpro.InterProAc;

/**
 * @author jluo
 * @date: 9 Apr 2021
 */
public class InterProAcBuilder extends AbstractValueBuilder<InterProAc> {

    public InterProAcBuilder(String value) {
        super(value);
    }

    @Override
    public @Nonnull InterProAc build() {
        return new InterProAcImpl(value);
    }

    public static @Nonnull InterProAcBuilder from(@Nonnull InterProAc instance) {
        return new InterProAcBuilder(instance.getValue());
    }
}
