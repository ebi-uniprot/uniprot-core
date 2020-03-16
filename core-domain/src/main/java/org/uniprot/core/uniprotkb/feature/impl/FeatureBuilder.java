package org.uniprot.core.uniprotkb.feature.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.feature.AlternativeSequence;
import org.uniprot.core.uniprotkb.feature.Feature;
import org.uniprot.core.uniprotkb.feature.FeatureDatabase;
import org.uniprot.core.uniprotkb.feature.FeatureDescription;
import org.uniprot.core.uniprotkb.feature.FeatureId;
import org.uniprot.core.uniprotkb.feature.FeatureLocation;
import org.uniprot.core.uniprotkb.feature.FeatureType;

/**
 * Created 22/01/19
 *
 * @author Edd
 */
public class FeatureBuilder implements Builder<Feature> {
    private FeatureType type;
    private FeatureLocation location;
    private FeatureDescription description;
    private FeatureId featureId;
    private AlternativeSequence alternativeSequence;
    private CrossReference<FeatureDatabase> featureCrossReference;
    private List<Evidence> evidences = new ArrayList<>();

    @Override
    public @Nonnull Feature build() {
        return new FeatureImpl(
                type,
                location,
                description,
                featureId,
                alternativeSequence,
                featureCrossReference,
                evidences);
    }

    public static @Nonnull FeatureBuilder from(@Nonnull Feature instance) {
        return new FeatureBuilder()
                .evidencesSet(instance.getEvidences())
                .description(instance.getDescription())
                .type(instance.getType())
                .location(instance.getLocation())
                .featureId(instance.getFeatureId())
                .alternativeSequence(instance.getAlternativeSequence())
                .featureCrossReference(instance.getFeatureCrossReference());
    }

    public @Nonnull FeatureBuilder type(FeatureType type) {
        this.type = type;
        return this;
    }

    public @Nonnull FeatureBuilder location(FeatureLocation location) {
        this.location = location;
        return this;
    }

    public @Nonnull FeatureBuilder description(String description) {
        this.description = new FeatureDescriptionImpl(description);
        return this;
    }

    public @Nonnull FeatureBuilder description(FeatureDescription description) {
        this.description = description;
        return this;
    }

    public @Nonnull FeatureBuilder featureId(FeatureId featureId) {
        this.featureId = featureId;
        return this;
    }

    public @Nonnull FeatureBuilder featureId(String featureId) {
        this.featureId = new FeatureIdImpl(featureId);
        return this;
    }

    public @Nonnull FeatureBuilder alternativeSequence(AlternativeSequence alternativeSequence) {
        this.alternativeSequence = alternativeSequence;
        return this;
    }

    public @Nonnull FeatureBuilder featureCrossReference(
            CrossReference<FeatureDatabase> featureCrossReference) {
        this.featureCrossReference = featureCrossReference;
        return this;
    }

    public @Nonnull FeatureBuilder evidencesSet(List<Evidence> evidences) {
        this.evidences = modifiableList(evidences);
        return this;
    }

    public @Nonnull FeatureBuilder evidencesAdd(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return this;
    }
}
