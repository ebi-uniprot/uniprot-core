package uk.ac.ebi.uniprot.domain.uniprot.description;

import java.util.List;



public interface ProteinDescription {

	ProteinName getRecommendedName();
	List<ProteinName> getAlternativeNames();
	List<ProteinName> getSubmissionNames();	
	Name getAllergenName();
	Name getBiotechName();
	List<Name> getCdAntigenNames();
	List<Name> getInnNames();
	List<ProteinNameSection> getIncludes();
	List<ProteinNameSection> getContains();
	boolean isValid();
	
}
