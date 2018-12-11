package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import uk.ac.ebi.uniprot.domain.impl.ECNumberImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ECImpl extends ECNumberImpl implements EC {
	private List<Evidence> evidences;

	private ECImpl(){
		super("");
		this.evidences = Collections.emptyList();
	}
	public ECImpl(String value,List<Evidence> evidences) {
		super(value);
		if ((evidences == null) || evidences.isEmpty()) {
			this.evidences = Collections.emptyList();
		} else {
			this.evidences = Collections.unmodifiableList(evidences);
		}
	}

	@Override
	public List<Evidence> getEvidences() {
		return evidences;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((evidences == null) ? 0 : evidences.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ECImpl other = (ECImpl) obj;
		if (evidences == null) {
			if (other.evidences != null)
				return false;
		} else if (!evidences.equals(other.evidences))
			return false;
		return true;
	}

	@Override
	public String getDisplayed(String separator) {
		StringBuilder sb = new StringBuilder();
		sb.append(getValue());
		if (!evidences.isEmpty()) {
			sb.append(separator)
					.append(evidences.stream().map(Evidence::getValue).collect(Collectors.joining(", ", "{", "}")));

		}
		return sb.toString();
	}

}
