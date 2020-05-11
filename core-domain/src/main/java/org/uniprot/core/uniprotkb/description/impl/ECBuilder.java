package org.uniprot.core.uniprotkb.description.impl;

import org.uniprot.core.uniprotkb.description.EC;
import org.uniprot.core.uniprotkb.evidence.impl.AbstractEvidencedValueBuilder;

import javax.annotation.Nonnull;

/** @author lgonzales */
public class ECBuilder extends AbstractEvidencedValueBuilder<ECBuilder, EC> {

    @Override
    protected @Nonnull ECBuilder getThis() {
        return this;
    }

    @Override
    public @Nonnull EC build() {
        return new ECImpl(value, evidences);
    }

    public static @Nonnull ECBuilder from(@Nonnull EC instance) {
        ECBuilder builder = new ECBuilder();
        AbstractEvidencedValueBuilder.init(builder, instance);
        return builder;
    }
}
