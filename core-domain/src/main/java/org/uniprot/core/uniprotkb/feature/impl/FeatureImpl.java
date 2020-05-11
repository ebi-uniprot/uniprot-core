package org.uniprot.core.uniprotkb.feature.impl;

import java.util.Collections;
import java.util.List;

import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.feature.AlternativeSequence;
import org.uniprot.core.uniprotkb.feature.AlternativeSequenceHelper;
import org.uniprot.core.uniprotkb.feature.Feature;
import org.uniprot.core.uniprotkb.feature.FeatureDatabase;
import org.uniprot.core.uniprotkb.feature.FeatureDescription;
import org.uniprot.core.uniprotkb.feature.FeatureId;
import org.uniprot.core.uniprotkb.feature.FeatureLocation;
import org.uniprot.core.uniprotkb.feature.FeatureType;
import org.uniprot.core.util.Utils;

public class FeatureImpl implements Feature {
    private static final long serialVersionUID = -5308576363211194641L;
    private FeatureType type;
    private FeatureLocation location;
    private FeatureDescription description;
    private FeatureId featureId;
    private AlternativeSequence alternativeSequence;
    private CrossReference<FeatureDatabase> featureCrossReference;
    private List<Evidence> evidences;

    // no arg constructor for JSON deserialization
    FeatureImpl() {
        evidences = Collections.emptyList();
    }

    FeatureImpl(
            FeatureType type,
            FeatureLocation location,
            FeatureDescription description,
            FeatureId featureId,
            AlternativeSequence alternativeSequence,
            CrossReference<FeatureDatabase> featureCrossReference,
            List<Evidence> evidences) {

        this.type = type;
        this.location = location;
        this.description = description;
        this.featureId = featureId;
        this.alternativeSequence = alternativeSequence;
        this.featureCrossReference = featureCrossReference;
        this.evidences = Utils.unmodifiableList(evidences);
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public boolean hasEvidences() {
        return Utils.notNullNotEmpty(this.evidences);
    }

    @Override
    public FeatureType getType() {
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
    public FeatureId getFeatureId() {
        return featureId;
    }

    @Override
    public boolean hasFeatureId() {
        return this.featureId != null && FeatureIdImpl.hasFeatureId(type);
    }

    @Override
    public AlternativeSequence getAlternativeSequence() {
        return alternativeSequence;
    }

    @Override
    public boolean hasAlternativeSequence() {
        return AlternativeSequenceHelper.hasAlternativeSequence(type);
    }

    @Override
    public boolean hasFeatureCrossReference() {
        return this.featureCrossReference != null;
    }

    @Override
    public CrossReference<FeatureDatabase> getFeatureCrossReference() {
        return featureCrossReference;
    }

    @Override
    public boolean hasLocation() {
        return this.location != null;
    }

    @Override
    public boolean hasDescription() {
        return this.description != null && this.description.hasValue();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result =
                prime * result
                        + ((alternativeSequence == null) ? 0 : alternativeSequence.hashCode());
        result =
                prime * result
                        + ((featureCrossReference == null) ? 0 : featureCrossReference.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((evidences == null) ? 0 : evidences.hashCode());
        result = prime * result + ((featureId == null) ? 0 : featureId.hashCode());
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        FeatureImpl other = (FeatureImpl) obj;
        if (alternativeSequence == null) {
            if (other.alternativeSequence != null) return false;
        } else if (!alternativeSequence.equals(other.alternativeSequence)) return false;
        if (featureCrossReference == null) {
            if (other.featureCrossReference != null) return false;
        } else if (!featureCrossReference.equals(other.featureCrossReference)) return false;
        if (description == null) {
            if (other.description != null) return false;
        } else if (!description.equals(other.description)) return false;
        if (!evidences.equals(other.evidences)) return false;
        if (featureId == null) {
            if (other.featureId != null) return false;
        } else if (!featureId.equals(other.featureId)) return false;
        if (location == null) {
            if (other.location != null) return false;
        } else if (!location.equals(other.location)) return false;
        return type == other.type;
    }
}
