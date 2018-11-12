package uk.ac.ebi.uniprot.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Property implements Pair<String, String>, Comparable<Property>{
	private final String key;
	private final String value;
	@JsonCreator
	public Property(@JsonProperty("key")String key, 
			@JsonProperty("value")String value) {
		this.key = key;
		this.value = value;
	}
	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getValue() {
		return value;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
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
		Property other = (Property) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	@Override
	public int compareTo(Property o) {
		return this.key.compareTo(o.getKey());
	}

}
