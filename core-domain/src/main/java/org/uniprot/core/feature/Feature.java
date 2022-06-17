package org.uniprot.core.feature;

import java.util.List;

import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprotkb.evidence.HasEvidences;
import org.uniprot.core.util.Utils;

/**
 * @author lgonzales
 * @since 04/05/2020
 */
public interface Feature<T extends FeatureDatabase, F extends FeatureType> extends HasEvidences {

    F getType();

    FeatureLocation getLocation();

    FeatureDescription getDescription();

    List<CrossReference<T>> getFeatureCrossReferences();

    default boolean hasLocation() {
        return Utils.notNull(getLocation());
    }

    default boolean hasDescription() {
        return Utils.notNull(getDescription());
    }

    default boolean hasFeatureCrossReference() {
        return Utils.notNullNotEmpty(getFeatureCrossReferences());
    }
}
