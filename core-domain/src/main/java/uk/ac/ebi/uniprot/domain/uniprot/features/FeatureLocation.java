package uk.ac.ebi.uniprot.domain.uniprot.features;


import uk.ac.ebi.uniprot.domain.exception.FieldUnavailableException;


public interface FeatureLocation {
	public FeatureLocationModifier getStartModifier();
	public int getStart() throws FieldUnavailableException;
	public boolean isStartAvailable();
	
	public int getEnd() throws FieldUnavailableException;
	public FeatureLocationModifier getEndModifier();
	public boolean isEndAvailable();

}

