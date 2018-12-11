package uk.ac.ebi.uniprot.domain.uniprot.feature;

import uk.ac.ebi.uniprot.domain.Value;

public interface FeatureId extends Value {
	boolean isValid(FeatureType type);
	
}
