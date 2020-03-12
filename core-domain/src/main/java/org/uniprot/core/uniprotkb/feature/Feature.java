package org.uniprot.core.uniprotkb.feature;

import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprotkb.evidence.HasEvidences;

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
