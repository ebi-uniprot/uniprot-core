package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import java.util.Collections;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.description.ECNumber;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;

public class ProteinNameImpl implements ProteinName {
	private final Name fullName;
	private final List<Name> shortNames;
	private final List<ECNumber> eCNumbers;
	public ProteinNameImpl(Name fullName,
			List<Name> shortNames,
			List<ECNumber> eCNumbers) {
		this.fullName = fullName;
		if((shortNames ==null) || (shortNames.isEmpty())) {
			this.shortNames = Collections.emptyList();
		}else {
			this.shortNames = Collections.unmodifiableList(shortNames);
		}
		if((eCNumbers ==null) || (eCNumbers.isEmpty())) {
			this.eCNumbers = Collections.emptyList();
		}else {
			this.eCNumbers = Collections.unmodifiableList(eCNumbers);
		}
	}
	@Override
	public Name getFullName() {
		return fullName;
	}
	@Override
	public List<Name> getShortNames() {
		return shortNames;
	}
	
	@Override
	public List<ECNumber> getEcNumbers() {
		return eCNumbers;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eCNumbers == null) ? 0 : eCNumbers.hashCode());
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + ((shortNames == null) ? 0 : shortNames.hashCode());
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
		ProteinNameImpl other = (ProteinNameImpl) obj;
		if (eCNumbers == null) {
			if (other.eCNumbers != null)
				return false;
		} else if (!eCNumbers.equals(other.eCNumbers))
			return false;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		if (shortNames == null) {
			if (other.shortNames != null)
				return false;
		} else if (!shortNames.equals(other.shortNames))
			return false;
		return true;
	}

}
