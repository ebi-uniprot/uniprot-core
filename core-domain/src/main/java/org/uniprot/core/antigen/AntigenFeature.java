package org.uniprot.core.antigen;

import org.uniprot.core.feature.Feature;

/**
 * @author lgonzales
 * @since 04/05/2020
 */
public interface AntigenFeature extends Feature<AntigenDatabase, AntigenFeatureType> {

    int getMatchScore();

    default boolean hasMatchScore() {
        return getMatchScore() > 0;
    }
}
