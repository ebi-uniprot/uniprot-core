package org.uniprot.core.uniprot.feature;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.Range;
import org.uniprot.core.uniprot.evidence.HasEvidences;

public interface Feature extends HasEvidences {
    FeatureType getType();

    Range getLocation();

    FeatureDescription getDescription();

    FeatureId getFeatureId();

    AlternativeSequence getAlternativeSequence();

    DBCrossReference<FeatureXDbType> getDbXref();

    boolean hasLocation();

    boolean hasDescription();

    boolean hasFeatureId();

    boolean hasAlternativeSequence();

    boolean hasDbXref();
}
