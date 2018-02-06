package uk.ac.ebi.uniprot.domain.uniprot.description;

public interface ProteinRecommendedName  extends ProteinName{
	default boolean isValid() {
		return (getFullName() !=null) &&
				( (getFullName().getValue() !=null)
						&& !getFullName().getValue().isEmpty());
	}
}
