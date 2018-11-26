package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.impl.ECNumberImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ECImpl extends ECNumberImpl implements EC {
	private final List<Evidence> evidences;

	@JsonCreator
	public ECImpl(@JsonProperty("value") String value, @JsonProperty("evidences") List<Evidence> evidences) {
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
