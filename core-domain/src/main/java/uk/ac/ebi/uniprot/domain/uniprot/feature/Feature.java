package uk.ac.ebi.uniprot.domain.uniprot.feature;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;

public interface Feature extends HasEvidences {
    FeatureType getType();

    Range getLocation();

    FeatureDescription getDescription();

    FeatureId getFeatureId();

    boolean hasFeatureId();

    AlternativeSequence getAlternativeSequence();

    boolean hasAlternativeSequence();

    DBCrossReference<FeatureXDbType> getDbXref();
}
