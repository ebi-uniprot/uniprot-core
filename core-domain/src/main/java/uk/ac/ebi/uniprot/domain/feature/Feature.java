package uk.ac.ebi.uniprot.domain.feature;

public interface Feature  {
	 FeatureType getType();
	 FeatureLocation getFeatureLocation();
	 FeatureDescription getDescription();
	 
}
