package org.uniprot.core.uniprot.feature.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.impl.AbstractValueBuilder;
import org.uniprot.core.uniprot.feature.FeatureId;

/**
 * Created 30/01/19
 *
 * @author Edd
 */
public class FeatureIdBuilder extends AbstractValueBuilder<FeatureId> {
    @Override
    public @Nonnull FeatureId build() {
        return new FeatureIdImpl(value);
    }

    public FeatureIdBuilder(String value) {
        super(value);
    }

    public static @Nonnull FeatureIdBuilder from(@Nonnull FeatureId instance) {
        return new FeatureIdBuilder(instance.getValue());
    }
}
