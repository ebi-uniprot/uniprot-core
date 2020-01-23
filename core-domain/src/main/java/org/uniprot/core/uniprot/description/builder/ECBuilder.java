package org.uniprot.core.uniprot.description.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprot.description.EC;
import org.uniprot.core.uniprot.description.impl.ECImpl;
import org.uniprot.core.uniprot.evidence.builder.AbstractEvidencedValueBuilder;

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
