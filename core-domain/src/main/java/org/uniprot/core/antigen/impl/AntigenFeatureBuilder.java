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

    private String antigenSequence;
    private int matchScore;

    public @Nonnull AntigenFeatureBuilder matchScore(int matchScore) {
        this.matchScore = matchScore;
        return getThis();
    }

    public @Nonnull AntigenFeatureBuilder antigenSequence(String antigenSequence) {
        this.antigenSequence = antigenSequence;
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
                featureCrossReference,
                evidences,
                antigenSequence,
                matchScore);
    }

    public static @Nonnull AntigenFeatureBuilder from(@Nonnull AntigenFeature instance) {
        AntigenFeatureBuilder builder = new AntigenFeatureBuilder();
        return AbstractFeatureBuilder.from(builder, instance)
                .antigenSequence(instance.getAntigenSequence())
                .matchScore(instance.getMatchScore());
    }
}
