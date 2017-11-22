package uk.ac.ebi.uniprot.domain.feature;

public interface FeatureLocation {
	public FeatureLocationModifier getStartModifier();
	public Integer getStart();
	public boolean isStartAvailable();
	
	public Integer getEnd();
	public FeatureLocationModifier getEndModifier();
	public boolean isEndAvailable();

}

