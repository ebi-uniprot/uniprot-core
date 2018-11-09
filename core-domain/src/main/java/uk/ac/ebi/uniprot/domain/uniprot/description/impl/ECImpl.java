package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import uk.ac.ebi.uniprot.domain.common.impl.ECNumberImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ECImpl extends ECNumberImpl implements EC {
	private final List<Evidence> evidences;
	public ECImpl(String value, List<Evidence> evidences) {
		super(value);
		if((evidences ==null) || evidences.isEmpty()) {
			this.evidences =Collections.emptyList();
		}else {
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

}
