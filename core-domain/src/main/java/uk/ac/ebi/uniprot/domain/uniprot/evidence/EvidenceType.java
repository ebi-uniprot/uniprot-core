package uk.ac.ebi.uniprot.domain.uniprot.evidence;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.DatabaseType;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public final class EvidenceType implements DatabaseType {
	private final String name;
	
	@JsonCreator
	public EvidenceType(@JsonProperty("name")String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	@JsonIgnore
	public EvidenceTypeDetail getDetail() {
		return EvidenceTypes.INSTANCE.getType(name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		EvidenceType other = (EvidenceType) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	

}
