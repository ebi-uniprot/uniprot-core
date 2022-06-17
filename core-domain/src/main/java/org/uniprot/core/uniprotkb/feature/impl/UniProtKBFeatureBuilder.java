package org.uniprot.core.uniprotkb.feature.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.feature.impl.AbstractFeatureBuilder;
import org.uniprot.core.uniprotkb.feature.*;

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

    private AlternativeSequence alternativeSequence;
    private FeatureId featureId;
    private Ligand ligand;
    private LigandPart ligandPart;

    @Override
    public @Nonnull UniProtKBFeature build() {
        return new UniProtKBFeatureImpl(
                type,
                location,
                description,
                featureId,
                alternativeSequence,
                ligand,
                ligandPart,
                featureCrossReferences,
                evidences);
    }

    public @Nonnull UniProtKBFeatureBuilder featureId(FeatureId featureId) {
        this.featureId = featureId;
        return getThis();
    }

    public @Nonnull UniProtKBFeatureBuilder featureId(String featureId) {
        this.featureId = new FeatureIdImpl(featureId);
        return this;
    }

    public @Nonnull UniProtKBFeatureBuilder alternativeSequence(
            AlternativeSequence alternativeSequence) {
        this.alternativeSequence = alternativeSequence;
        return this;
    }

    public @Nonnull UniProtKBFeatureBuilder ligand(
            Ligand ligand) {
        this.ligand = ligand;
        return this;
    }
    
    public @Nonnull UniProtKBFeatureBuilder ligandPart(
            LigandPart ligandPart) {
        this.ligandPart = ligandPart;
        return this;
    }
    
    public static @Nonnull UniProtKBFeatureBuilder from(@Nonnull UniProtKBFeature instance) {
        UniProtKBFeatureBuilder builder = new UniProtKBFeatureBuilder();
        return AbstractFeatureBuilder.from(builder, instance)
                .alternativeSequence(instance.getAlternativeSequence())
                .featureId(instance.getFeatureId())
                .ligand(instance.getLigand())
                .ligandPart(instance.getLigandPart());
    }

    @Nonnull
    @Override
    protected UniProtKBFeatureBuilder getThis() {
        return this;
    }
}
