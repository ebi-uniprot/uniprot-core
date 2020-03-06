package org.uniprot.core.uniprot.feature;

import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprot.evidence.HasEvidences;

public interface Feature extends HasEvidences {
    FeatureType getType();

    FeatureLocation getLocation();

    FeatureDescription getDescription();

    FeatureId getFeatureId();

    AlternativeSequence getAlternativeSequence();

    CrossReference<FeatureDatabase> getFeatureCrossReference();

    boolean hasLocation();

    boolean hasDescription();

    boolean hasFeatureId();

    boolean hasAlternativeSequence();

    boolean hasFeatureCrossReference();
}
