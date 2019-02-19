package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import java.util.Collections;
import java.util.List;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinAltName;

public class ProteinAltNameImpl implements ProteinAltName {

	private static final long serialVersionUID = -4837827085627262838L;
	private Name fullName;
	private List<Name> shortNames;
	private List<EC> ecNumbers;
	private Name allergenName;
	private Name biotechName;
	private List<Name> cdAntigenNames;
	private List<Name> innNames;

	private ProteinAltNameImpl() {
		shortNames = Collections.emptyList();
		ecNumbers = Collections.emptyList();
		this.cdAntigenNames = Collections.emptyList();
		this.innNames = Collections.emptyList();
	}

	public ProteinAltNameImpl(Name fullName, List<Name> shortNames, List<EC> ecNumbers, Name allergenName,
			Name biotechName, List<Name> cdAntigenNames, List<Name> innNames) {
		this.fullName = fullName;
		if ((shortNames == null) || (shortNames.isEmpty())) {
			this.shortNames = Collections.emptyList();
		} else {
			this.shortNames = Collections.unmodifiableList(shortNames);
		}
		if ((ecNumbers == null) || (ecNumbers.isEmpty())) {
			this.ecNumbers = Collections.emptyList();
		} else {
			this.ecNumbers = Collections.unmodifiableList(ecNumbers);
		}
		this.allergenName = allergenName;
		this.biotechName = biotechName;
		this.cdAntigenNames = Utils.nonNullUnmodifiableList(cdAntigenNames);
		this.innNames = Utils.nonNullUnmodifiableList(innNames);
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
	public List<EC> getEcNumbers() {
		return ecNumbers;
	}

	@Override
	public boolean hasFullName() {
		return this.fullName != null;
	}

	@Override
	public boolean hasShortNames() {
		return this.shortNames != null;
	}

	@Override
	public boolean hasEcNumbers() {
		return this.ecNumbers != null;
	}

	@Override
	public boolean isValid() {
		return (getFullName() != null) && ((getFullName().getValue() != null) && !getFullName().getValue().isEmpty());
	}

	@Override
	public Name getAllergenName() {
		return allergenName;
	}

	@Override
	public Name getBiotechName() {
		return this.biotechName;
	}

	@Override
	public List<Name> getCdAntigenNames() {
		return this.cdAntigenNames;
	}

	@Override
	public List<Name> getInnNames() {
		return this.innNames;
	}

	@Override
	public boolean hasAllergenName() {
		return this.allergenName != null;
	}

	@Override
	public boolean hasBiotechName() {
		return this.biotechName != null;
	}

	@Override
	public boolean hasCdAntigenNames() {
		return Utils.notEmpty(this.cdAntigenNames);
	}

	@Override
	public boolean hasInnNames() {
		return Utils.notEmpty(this.innNames);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allergenName == null) ? 0 : allergenName.hashCode());
		result = prime * result + ((biotechName == null) ? 0 : biotechName.hashCode());
		result = prime * result + ((cdAntigenNames == null) ? 0 : cdAntigenNames.hashCode());
		result = prime * result + ((ecNumbers == null) ? 0 : ecNumbers.hashCode());
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + ((innNames == null) ? 0 : innNames.hashCode());
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
		ProteinAltNameImpl other = (ProteinAltNameImpl) obj;
		if (allergenName == null) {
			if (other.allergenName != null)
				return false;
		} else if (!allergenName.equals(other.allergenName))
			return false;
		if (biotechName == null) {
			if (other.biotechName != null)
				return false;
		} else if (!biotechName.equals(other.biotechName))
			return false;
		if (cdAntigenNames == null) {
			if (other.cdAntigenNames != null)
				return false;
		} else if (!cdAntigenNames.equals(other.cdAntigenNames))
			return false;
		if (ecNumbers == null) {
			if (other.ecNumbers != null)
				return false;
		} else if (!ecNumbers.equals(other.ecNumbers))
			return false;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		if (innNames == null) {
			if (other.innNames != null)
				return false;
		} else if (!innNames.equals(other.innNames))
			return false;
		if (shortNames == null) {
			if (other.shortNames != null)
				return false;
		} else if (!shortNames.equals(other.shortNames))
			return false;
		return true;
	}

}
