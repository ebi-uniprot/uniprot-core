package org.uniprot.core.uniprotkb.feature.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.feature.impl.AbstractFeatureBuilder;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeatureId;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureDatabase;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;

/**
 * Created 22/01/19
 *
 * @author Edd
 */
public class UniProtKBFeatureBuilder
        extends AbstractFeatureBuilder<
                UniProtKBFeatureBuilder,
                UniProtKBFeature,
                UniprotKBFeatureDatabase,
                UniprotKBFeatureType> {

    private UniProtKBFeatureId featureId;

    @Override
    public @Nonnull UniProtKBFeature build() {
        return new UniProtKBFeatureImpl(
                type,
                location,
                description,
                featureId,
                alternativeSequence,
                featureCrossReference,
                evidences);
    }

    public @Nonnull UniProtKBFeatureBuilder featureId(UniProtKBFeatureId featureId) {
        this.featureId = featureId;
        return getThis();
    }

    public @Nonnull UniProtKBFeatureBuilder featureId(String featureId) {
        this.featureId = new UniProtKBFeatureIdImpl(featureId);
        return this;
    }

    public static @Nonnull UniProtKBFeatureBuilder from(@Nonnull UniProtKBFeature instance) {
        UniProtKBFeatureBuilder builder = new UniProtKBFeatureBuilder();
        return AbstractFeatureBuilder.from(builder, instance).featureId(instance.getFeatureId());
    }

    @Nonnull
    @Override
    protected UniProtKBFeatureBuilder getThis() {
        return this;
    }
}
