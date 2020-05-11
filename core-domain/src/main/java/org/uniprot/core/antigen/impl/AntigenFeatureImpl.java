package org.uniprot.core.antigen.impl;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.CrossReference;
import org.uniprot.core.antigen.AntigenDatabase;
import org.uniprot.core.antigen.AntigenFeature;
import org.uniprot.core.antigen.AntigenFeatureType;
import org.uniprot.core.feature.AlternativeSequence;
import org.uniprot.core.feature.FeatureDescription;
import org.uniprot.core.feature.FeatureLocation;
import org.uniprot.core.feature.impl.AbstractFeature;
import org.uniprot.core.uniprotkb.evidence.Evidence;

/**
 * @author lgonzales
 * @since 06/05/2020
 */
public class AntigenFeatureImpl extends AbstractFeature<AntigenDatabase, AntigenFeatureType>
        implements AntigenFeature {

    private static final long serialVersionUID = 1066318168400130113L;
    private final int matchScore;

    // no arg constructor for JSON deserialization
    AntigenFeatureImpl() {
        this(null, null, null, null, null, null, 0);
    }

    AntigenFeatureImpl(
            AntigenFeatureType type,
            FeatureLocation location,
            FeatureDescription description,
            AlternativeSequence alternativeSequence,
            CrossReference<AntigenDatabase> featureCrossReference,
            List<Evidence> evidences,
            int matchScore) {
        super(type, location, description, alternativeSequence, featureCrossReference, evidences);
        this.matchScore = matchScore;
    }

    @Override
    public int getMatchScore() {
        return matchScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AntigenFeatureImpl that = (AntigenFeatureImpl) o;
        return getMatchScore() == that.getMatchScore();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getMatchScore());
    }
}
