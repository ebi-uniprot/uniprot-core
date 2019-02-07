package uk.ac.ebi.uniprot.domain.uniprot.feature;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.HasEvidences;

public interface Feature extends HasEvidences {
    FeatureType getType();

    Range getLocation();

    FeatureDescription getDescription();

    FeatureId getFeatureId();

    AlternativeSequence getAlternativeSequence();

    DBCrossReference<FeatureXDbType> getDbXref();

    boolean hastLocation();

    boolean hasDescription();

    boolean hasFeatureId();

    boolean hasAlternativeSequence();

    boolean hasDbXref();
}
