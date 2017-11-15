package uk.ac.ebi.uniprot.domain.uniprot.features;

import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;

public interface Feature extends HasEvidences, HasFeatureStatus {

	public FeatureType getType();
	public FeatureLocation getFeatureLocation();

}
