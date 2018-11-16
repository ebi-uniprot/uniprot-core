package uk.ac.ebi.uniprot.domain.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.DatabaseType;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public final class DefaultDatabaseType implements DatabaseType {
	private final String name;
	@JsonCreator
	public DefaultDatabaseType(@JsonProperty("name") String name) {
		this.name = name;
	}
	@Override
	public String getName() {
		return name;
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
		DefaultDatabaseType other = (DefaultDatabaseType) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
