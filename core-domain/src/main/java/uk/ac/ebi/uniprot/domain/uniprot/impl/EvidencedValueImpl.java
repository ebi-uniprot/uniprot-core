package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EvidencedValueImpl implements EvidencedValue {
	private final String value;
	private final List<Evidence> evidences;
	@JsonCreator
	public EvidencedValueImpl(
			@JsonProperty("value") String value, 
			@JsonProperty("evidences") List<Evidence> evidences) {
		if (value == null) {
			this.value = "";
		} else
			this.value = value;
		if ((evidences != null) && !evidences.isEmpty())
			this.evidences = Collections.unmodifiableList(evidences);
		else
			this.evidences = Collections.emptyList();
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public List<Evidence> getEvidences() {
		return evidences;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((evidences == null) ? 0 : evidences.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		EvidencedValueImpl other = (EvidencedValueImpl) obj;
		if (evidences == null) {
			if (other.evidences != null)
				return false;
		} else if (!evidences.equals(other.evidences))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getDisplayed(" ");
	}

	@Override
	public String getDisplayed(String separator) {
		StringBuilder sb = new StringBuilder();
		sb.append(value);
		if (!evidences.isEmpty()) {
			sb.append(separator)
					.append(evidences.stream().map(Evidence::getValue).collect(Collectors.joining(", ", "{", "}")));

		}
		return sb.toString();
	}

}
