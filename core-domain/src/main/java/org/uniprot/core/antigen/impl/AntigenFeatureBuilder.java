package org.uniprot.core.antigen.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.antigen.AntigenDatabase;
import org.uniprot.core.antigen.AntigenFeature;
import org.uniprot.core.antigen.AntigenFeatureType;
import org.uniprot.core.feature.impl.AbstractFeatureBuilder;

/**
 * @author lgonzales
 * @since 06/05/2020
 */
public class AntigenFeatureBuilder
        extends AbstractFeatureBuilder<
                AntigenFeatureBuilder, AntigenFeature, AntigenDatabase, AntigenFeatureType> {

    private int matchScore;

    public @Nonnull AntigenFeatureBuilder matchScore(int matchScore) {
        this.matchScore = matchScore;
        return getThis();
    }

    @Nonnull
    @Override
    protected AntigenFeatureBuilder getThis() {
        return this;
    }

    @Nonnull
    @Override
    public AntigenFeature build() {
        return new AntigenFeatureImpl(
                type,
                location,
                description,
                alternativeSequence,
                featureCrossReference,
                evidences,
                matchScore);
    }

    public static @Nonnull AntigenFeatureBuilder from(@Nonnull AntigenFeature instance) {
        AntigenFeatureBuilder builder = new AntigenFeatureBuilder();
        return AbstractFeatureBuilder.from(builder, instance).matchScore(instance.getMatchScore());
    }
}
