package org.uniprot.core.uniprotkb.feature.impl;

import java.util.*;

import org.uniprot.core.CrossReference;
import org.uniprot.core.feature.*;
import org.uniprot.core.feature.impl.AbstractFeature;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.feature.*;
import org.uniprot.core.util.Utils;

public class UniProtKBFeatureImpl
        extends AbstractFeature<UniprotKBFeatureDatabase, UniprotKBFeatureType>
        implements UniProtKBFeature {

    private static final long serialVersionUID = -5308576363211194641L;
    private final UniProtKBFeatureId featureId;

    // no arg constructor for JSON deserialization
    UniProtKBFeatureImpl() {
        this(null, null, null, null, null, null, null);
    }

    UniProtKBFeatureImpl(
            UniprotKBFeatureType type,
            FeatureLocation location,
            FeatureDescription description,
            UniProtKBFeatureId featureId,
            AlternativeSequence alternativeSequence,
            CrossReference<UniprotKBFeatureDatabase> featureCrossReference,
            List<Evidence> evidences) {
        super(type, location, description, alternativeSequence, featureCrossReference, evidences);
        this.featureId = featureId;
    }

    @Override
    public UniProtKBFeatureId getFeatureId() {
        return featureId;
    }

    @Override
    public boolean hasAlternativeSequence() {
        return super.hasAlternativeSequence()
                && UniprotKBAlternativeSequenceHelper.hasAlternativeSequence(getType());
    }

    @Override
    public boolean hasFeatureId() {
        return Utils.notNull(this.featureId) && UniProtKBFeatureIdImpl.hasFeatureId(getType());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UniProtKBFeatureImpl that = (UniProtKBFeatureImpl) o;
        return Objects.equals(getFeatureId(), that.getFeatureId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFeatureId());
    }
}
