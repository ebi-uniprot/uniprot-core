package org.uniprot.core.feature.impl;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.CrossReference;
import org.uniprot.core.feature.*;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.util.Utils;

public abstract class AbstractFeature<T extends FeatureDatabase, F extends FeatureType>
        implements Feature<T, F> {
    private static final long serialVersionUID = -5308576363211194641L;
    private final F type;
    private final FeatureLocation location;
    private final FeatureDescription description;
    private final AlternativeSequence alternativeSequence;
    private final CrossReference<T> featureCrossReference;
    private final List<Evidence> evidences;

    protected AbstractFeature(
            F type,
            FeatureLocation location,
            FeatureDescription description,
            AlternativeSequence alternativeSequence,
            CrossReference<T> featureCrossReference,
            List<Evidence> evidences) {

        this.type = type;
        this.location = location;
        this.description = description;
        this.alternativeSequence = alternativeSequence;
        this.featureCrossReference = featureCrossReference;
        this.evidences = Utils.unmodifiableList(evidences);
    }

    @Override
    public F getType() {
        return type;
    }

    @Override
    public FeatureLocation getLocation() {
        return location;
    }

    @Override
    public FeatureDescription getDescription() {
        return description;
    }

    @Override
    public AlternativeSequence getAlternativeSequence() {
        return alternativeSequence;
    }

    @Override
    public CrossReference<T> getFeatureCrossReference() {
        return featureCrossReference;
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractFeature<?, ?> that = (AbstractFeature<?, ?>) o;
        return Objects.equals(getType(), that.getType())
                && Objects.equals(getLocation(), that.getLocation())
                && Objects.equals(getDescription(), that.getDescription())
                && Objects.equals(getAlternativeSequence(), that.getAlternativeSequence())
                && Objects.equals(getFeatureCrossReference(), that.getFeatureCrossReference())
                && Objects.equals(getEvidences(), that.getEvidences());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getType(),
                getLocation(),
                getDescription(),
                getAlternativeSequence(),
                getFeatureCrossReference(),
                getEvidences());
    }
}
