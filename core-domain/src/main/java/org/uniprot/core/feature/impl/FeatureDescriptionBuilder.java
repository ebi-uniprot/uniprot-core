package org.uniprot.core.feature.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.feature.FeatureDescription;
import org.uniprot.core.impl.AbstractValueBuilder;

/**
 * @author lgonzales
 * @since 04/05/2020
 */
public class FeatureDescriptionBuilder extends AbstractValueBuilder<FeatureDescription> {

    public FeatureDescriptionBuilder(String value) {
        super(value);
    }

    @Nonnull
    @Override
    public FeatureDescription build() {
        return new FeatureDescriptionImpl(value);
    }

    public static @Nonnull FeatureDescriptionBuilder from(@Nonnull FeatureDescription instance) {
        return new FeatureDescriptionBuilder(instance.getValue());
    }
}
