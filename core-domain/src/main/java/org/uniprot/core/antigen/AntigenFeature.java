package org.uniprot.core.antigen;

import org.uniprot.core.feature.Feature;
import org.uniprot.core.util.Utils;

/**
 * @author lgonzales
 * @since 04/05/2020
 */
public interface AntigenFeature extends Feature<AntigenDatabase, AntigenFeatureType> {

    int getMatchScore();

    String getAntigenSequence();

    default boolean hasMatchScore() {
        return getMatchScore() > 0;
    }

    default boolean hasAntigenSequence() {
        return Utils.notNullNotEmpty(getAntigenSequence());
    }
}
