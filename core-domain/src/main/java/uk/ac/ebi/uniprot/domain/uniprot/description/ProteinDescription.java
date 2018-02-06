package uk.ac.ebi.uniprot.domain.uniprot.description;

import java.util.List;

public interface ProteinDescription {

	ProteinRecommendedName getRecommendedName();

	List<ProteinSubmissionName> getSubmissionNames();

	ProteinAlternativeName getAlternativeName();

	Flag getFlag();

	List<ProteinNameSection> getIncludes();

	List<ProteinNameSection> getContains();

	default boolean isValid() {
		if (getRecommendedName() != null) {
			return getRecommendedName().isValid();
		} else {
			return getSubmissionNames().stream().anyMatch(val -> val.isValid());
		}
	}
}
