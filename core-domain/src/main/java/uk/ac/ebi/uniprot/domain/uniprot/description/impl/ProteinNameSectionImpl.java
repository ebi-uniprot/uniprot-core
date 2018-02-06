package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinAlternativeName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinNameSection;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinRecommendedName;

public class ProteinNameSectionImpl implements ProteinNameSection {
	private final ProteinRecommendedName recName;
	private final ProteinAlternativeName altName;
	public ProteinNameSectionImpl(ProteinRecommendedName recName, ProteinAlternativeName altName) {
		this.recName = recName;
		this.altName = altName;
	}
	@Override
	public ProteinRecommendedName getRecommendedName() {
		return recName;
	}

	@Override
	public ProteinAlternativeName getAlternativeName() {
		return altName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((altName == null) ? 0 : altName.hashCode());
		result = prime * result + ((recName == null) ? 0 : recName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProteinNameSectionImpl other = (ProteinNameSectionImpl) obj;
		if (altName == null) {
			if (other.altName != null)
				return false;
		} else if (!altName.equals(other.altName))
			return false;
		if (recName == null) {
			if (other.recName != null)
				return false;
		} else if (!recName.equals(other.recName))
			return false;
		return true;
	}

}
