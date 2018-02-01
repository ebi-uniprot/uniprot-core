package uk.ac.ebi.uniprot.domain.uniprot.description;

import java.util.List;

public interface RecName {
	ProteinName getFullName();
	List<ProteinName> getShortNames();
	List<ECNumber> getEcNumbers();

	default boolean isValid() {
		return (getFullName() !=null) &&
				( (getFullName().getValue() !=null)
						&& !getFullName().getValue().isEmpty());
	}
}
