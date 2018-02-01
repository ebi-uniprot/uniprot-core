package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.description.AltName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ECNumber;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;

public class AltNameImpl implements AltName {
	public static Builder newBuilder() {
		return new Builder();
	}
	public static class Builder{
		private ProteinName fullName;
		private List<ProteinName> shortNames;
		private List<ECNumber> ecNumbers;
		private ProteinName allergenName;
		private ProteinName biotechName;
		private List<ProteinName> cDAntigenNames;
		private List<ProteinName> iNNNames;
		
		public AltName build() {
			return new AltNameImpl( fullName, shortNames, ecNumbers, allergenName,
					biotechName, cDAntigenNames, iNNNames);
		}
		public Builder fullName(ProteinName fullName) {
			this.fullName = fullName;
			return this;
		}
		public Builder shortName( ProteinName shortName) {
			if(shortNames == null) {
				shortNames = new ArrayList<>();
			}
			shortNames.add(shortName);
			return this;
		}
		
		public Builder ecNumber( ECNumber ecNumber) {
			if(ecNumbers == null) {
				ecNumbers = new ArrayList<>();
			}
			ecNumbers.add(ecNumber);
			return this;
		}
		public Builder allergenName(ProteinName allergenName) {
			this.allergenName = allergenName;
			return this;
		}
		
		public Builder biotechName(ProteinName biotechName) {
			this.biotechName = biotechName;
			return this;
		}
		public Builder cDAntigenName( ProteinName cDAntigenName) {
			if(cDAntigenNames == null) {
				cDAntigenNames = new ArrayList<>();
			}
			cDAntigenNames.add(cDAntigenName);
			return this;
		}
		public Builder iNNName( ProteinName iNNName) {
			if(iNNNames == null) {
				iNNNames = new ArrayList<>();
			}
			iNNNames.add(iNNName);
			return this;
		}
	}
	
	private final ProteinName fullName;
	private final List<ProteinName> shortNames;
	private final List<ECNumber> eCNumbers;
	private final ProteinName allergenName;
	private final ProteinName biotechName;
	private final List<ProteinName> cDAntigenNames;
	private final List<ProteinName> iNNNames;
	public AltNameImpl(ProteinName fullName,
			List<ProteinName> shortNames,
			List<ECNumber> eCNumbers,
			ProteinName allergenName,
			ProteinName biotechName,
			List<ProteinName> cDAntigenNames,
			List<ProteinName> iNNNames) {
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
	public ProteinName getFullName() {
		return fullName;
	}
	@Override
	public List<ProteinName> getShortNames() {
		return shortNames;
	}
	
	@Override
	public List<ECNumber> getEcNumbers() {
		return eCNumbers;
	}

	@Override
	public ProteinName getAllergenName() {
		return allergenName;
	}

	@Override
	public ProteinName getBiotechName() {
		return biotechName;
	}

	@Override
	public List<ProteinName> getCDAntigenNames() {
		return cDAntigenNames;
	}

	@Override
	public List<ProteinName> getINNNames() {
		return iNNNames;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allergenName == null) ? 0 : allergenName.hashCode());
		result = prime * result + ((biotechName == null) ? 0 : biotechName.hashCode());
		result = prime * result + ((cDAntigenNames == null) ? 0 : cDAntigenNames.hashCode());
		result = prime * result + ((eCNumbers == null) ? 0 : eCNumbers.hashCode());
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + ((iNNNames == null) ? 0 : iNNNames.hashCode());
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
		AltNameImpl other = (AltNameImpl) obj;
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
		if (cDAntigenNames == null) {
			if (other.cDAntigenNames != null)
				return false;
		} else if (!cDAntigenNames.equals(other.cDAntigenNames))
			return false;
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
		if (iNNNames == null) {
			if (other.iNNNames != null)
				return false;
		} else if (!iNNNames.equals(other.iNNNames))
			return false;
		if (shortNames == null) {
			if (other.shortNames != null)
				return false;
		} else if (!shortNames.equals(other.shortNames))
			return false;
		return true;
	}

}
