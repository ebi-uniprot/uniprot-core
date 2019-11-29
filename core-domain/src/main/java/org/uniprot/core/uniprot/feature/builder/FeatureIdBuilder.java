package org.uniprot.core.uniprot.feature.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.builder.AbstractValueBuilder;
import org.uniprot.core.uniprot.feature.FeatureId;
import org.uniprot.core.uniprot.feature.impl.FeatureIdImpl;

/**
 * Created 30/01/19
 *
 * @author Edd
 */
public class FeatureIdBuilder extends AbstractValueBuilder<FeatureIdBuilder, FeatureId> {
    @Override
    public @Nonnull FeatureId build() {
        return new FeatureIdImpl(value);
    }

    @Override
    protected @Nonnull FeatureIdBuilder getThis() {
        return this;
    }

    public FeatureIdBuilder(String value) {
        super(value);
    }
}
