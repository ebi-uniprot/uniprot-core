package uk.ac.ebi.uniprot.domain.uniprot.features;

import uk.ac.ebi.uniprot.domain.common.Value;

public interface FeatureId extends Value {
    boolean isValidFeatureId();
}
