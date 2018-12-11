package uk.ac.ebi.uniprot.domain.impl;

import uk.ac.ebi.uniprot.domain.ECNumber;

import java.util.regex.Pattern;

public class ECNumberImpl implements ECNumber {
	private static final Pattern EC_PATTERN =Pattern.compile("\\d{1,2}(\\.(\\-|n?\\d{1,3})){3}");
	private String value;

	private ECNumberImpl(){
		this.value = "";
	}
	public ECNumberImpl(String value) {
		this.value = value;
	}
	
	@Override
	public String getValue() {
		return value;
	}

	@Override
	public boolean isValid() {
		return EC_PATTERN.matcher(value).matches();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		ECNumberImpl other = (ECNumberImpl) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
