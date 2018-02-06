package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import java.util.Collections;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.description.AltName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinAlternativeName;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;

public class ProteinAlternativeNameImpl implements ProteinAlternativeName {
	private final List<AltName> altNames;
	private final Name allergenName;
	private final Name biotechName;
	private final List<Name> cDAntigenNames;
	private final List<Name> iNNNames;
	public ProteinAlternativeNameImpl(List<AltName> altNames,
			Name allergenName,
			Name biotechName,
			List<Name> cDAntigenNames,
			List<Name> iNNNames) {
		if((altNames ==null) || (altNames.isEmpty())) {
			this.altNames = Collections.emptyList();
		}else {
			this.altNames = Collections.unmodifiableList(altNames);
		}
		this.allergenName = allergenName;
		this.biotechName = biotechName;
		if((cDAntigenNames ==null) || (cDAntigenNames.isEmpty())) {
			this.cDAntigenNames = Collections.emptyList();
		}else {
			this.cDAntigenNames = Collections.unmodifiableList(cDAntigenNames);
		}
		if((iNNNames ==null) || (iNNNames.isEmpty())) {
			this.iNNNames = Collections.emptyList();
		}else {
			this.iNNNames = Collections.unmodifiableList(iNNNames);
		}
	}
	
	@Override
	public List<AltName> getAltNames() {
		return altNames;
	}

	@Override
	public Name getAllergenName() {
		return allergenName;
	}

	@Override
	public Name getBiotechName() {
		return biotechName;
	}

	@Override
	public List<Name> getCDAntigenNames() {
		return cDAntigenNames;
	}

	@Override
	public List<Name> getINNNames() {
		return iNNNames;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allergenName == null) ? 0 : allergenName.hashCode());
		result = prime * result + ((altNames == null) ? 0 : altNames.hashCode());
		result = prime * result + ((biotechName == null) ? 0 : biotechName.hashCode());
		result = prime * result + ((cDAntigenNames == null) ? 0 : cDAntigenNames.hashCode());
		result = prime * result + ((iNNNames == null) ? 0 : iNNNames.hashCode());
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
		ProteinAlternativeNameImpl other = (ProteinAlternativeNameImpl) obj;
		if (allergenName == null) {
			if (other.allergenName != null)
				return false;
		} else if (!allergenName.equals(other.allergenName))
			return false;
		if (altNames == null) {
			if (other.altNames != null)
				return false;
		} else if (!altNames.equals(other.altNames))
			return false;
		if (biotechName == null) {
			if (other.biotechName != null)
				return false;
		} else if (!biotechName.equals(other.biotechName))
			return false;
		if (cDAntigenNames == null) {
			if (other.cDAntigenNames != null)
				return false;
		} else if (!cDAntigenNames.equals(other.cDAntigenNames))
			return false;
		if (iNNNames == null) {
			if (other.iNNNames != null)
				return false;
		} else if (!iNNNames.equals(other.iNNNames))
			return false;
		return true;
	}

}
