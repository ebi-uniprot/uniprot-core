package uk.ac.ebi.uniprot.domain.uniprot.description;

import java.util.List;

public interface ProteinNameSection {
	ProteinName getRecommendedName();
	List<ProteinName> getAlternativeNames();
}
