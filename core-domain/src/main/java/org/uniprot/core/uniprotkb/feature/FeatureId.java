package org.uniprot.core.uniprotkb.feature;

import org.uniprot.core.Value;

/**
 * @author lgonzales
 * @since 04/05/2020
 */
public interface FeatureId extends Value {

    boolean isValid(UniprotKBFeatureType type);
}
