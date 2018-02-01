package uk.ac.ebi.uniprot.domain.uniprot.description;

import java.util.List;

public interface ProteinRecommendedName {
	RecName getRecName();
	List<AltName> getAltNames();
	default boolean isValid() {
		return ((getRecName() !=null) && getRecName().isValid());
	}
}
