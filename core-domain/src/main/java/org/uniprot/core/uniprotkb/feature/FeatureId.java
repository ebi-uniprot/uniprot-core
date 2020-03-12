package org.uniprot.core.uniprotkb.feature;

import org.uniprot.core.Value;

public interface FeatureId extends Value {
    boolean isValid(FeatureType type);
}
