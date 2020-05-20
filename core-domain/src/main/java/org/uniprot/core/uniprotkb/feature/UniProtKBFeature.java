package org.uniprot.core.uniprotkb.feature;

import org.uniprot.core.feature.Feature;

public interface UniProtKBFeature extends Feature<UniprotKBFeatureDatabase, UniprotKBFeatureType> {

    FeatureId getFeatureId();

    AlternativeSequence getAlternativeSequence();

    boolean hasFeatureId();

    boolean hasAlternativeSequence();
}
