package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import java.util.Collections;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.description.AltName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinRecommendedName;
import uk.ac.ebi.uniprot.domain.uniprot.description.RecName;

public class ProteinRecommendedNameImpl implements ProteinRecommendedName {
	private final RecName recName;
	private final  List<AltName> altNames;
	public ProteinRecommendedNameImpl(RecName recName,
			List<AltName> altNames) {
		this.recName = recName;

		if((altNames ==null) || (altNames.isEmpty())) {
			this.altNames = Collections.emptyList();
		}else {
			this.altNames = Collections.unmodifiableList(altNames);
		}
	}
	
	@Override
	public RecName getRecName() {
		return recName;
	}

	@Override
	public List<AltName> getAltNames() {
		return altNames;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((altNames == null) ? 0 : altNames.hashCode());
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
		ProteinRecommendedNameImpl other = (ProteinRecommendedNameImpl) obj;
		if (altNames == null) {
			if (other.altNames != null)
				return false;
		} else if (!altNames.equals(other.altNames))
			return false;
		if (recName == null) {
			if (other.recName != null)
				return false;
		} else if (!recName.equals(other.recName))
			return false;
		return true;
	}

}
