package org.uniprot.core.uniprotkb.feature.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.impl.AbstractValueBuilder;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeatureId;

/**
 * Created 30/01/19
 *
 * @author Edd
 */
public class UniProtKBFeatureIdBuilder extends AbstractValueBuilder<UniProtKBFeatureId> {
    @Override
    public @Nonnull UniProtKBFeatureId build() {
        return new UniProtKBFeatureIdImpl(value);
    }

    public UniProtKBFeatureIdBuilder(String value) {
        super(value);
    }

    public static @Nonnull UniProtKBFeatureIdBuilder from(@Nonnull UniProtKBFeatureId instance) {
        return new UniProtKBFeatureIdBuilder(instance.getValue());
    }
}
