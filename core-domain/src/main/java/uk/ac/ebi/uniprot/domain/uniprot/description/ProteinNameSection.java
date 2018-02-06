package uk.ac.ebi.uniprot.domain.uniprot.description;

public interface ProteinNameSection {
	ProteinRecommendedName getRecommendedName();
	ProteinAlternativeName getAlternativeName();
}
