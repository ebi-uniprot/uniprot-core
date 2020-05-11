package org.uniprot.core.uniprotkb.feature;

import org.uniprot.core.feature.Feature;

public interface UniProtKBFeature extends Feature<UniprotKBFeatureDatabase, UniprotKBFeatureType> {

    UniProtKBFeatureId getFeatureId();

    boolean hasFeatureId();
}
