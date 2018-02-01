package uk.ac.ebi.uniprot.domain.uniprot.description;

import java.util.List;

public interface ProteinDescription {

	ProteinRecommendedName getRecommendedName();
	ProteinSubmissionName getSubmmissonName();
	Flag getFlag();
	List<ProteinRecommendedName> getIncludes();
	List<ProteinRecommendedName> getContains();

	default boolean isValid() {
		if(getRecommendedName() !=null) {
			return getRecommendedName().isValid();
		}else {
			return ((getSubmmissonName() !=null) && getSubmmissonName().isValid());
		}
	}
}
