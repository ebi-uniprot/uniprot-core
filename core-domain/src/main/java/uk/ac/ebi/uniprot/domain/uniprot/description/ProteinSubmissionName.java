package uk.ac.ebi.uniprot.domain.uniprot.description;

import java.util.List;

public interface ProteinSubmissionName {
	ProteinName getFullName();
	List<ECNumber> getEcNumbers();
	default boolean isValid() {
		return (getFullName() !=null) &&
				( (getFullName().getValue() !=null)
						&& !getFullName().getValue().isEmpty());
	}
}
