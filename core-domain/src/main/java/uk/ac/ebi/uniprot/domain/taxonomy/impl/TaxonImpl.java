package uk.ac.ebi.uniprot.domain.taxonomy.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.taxonomy.Taxon;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonName;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TaxonImpl implements Taxon {
	private final TaxonName name;
	private final long taxonId;

	@JsonCreator
	public TaxonImpl(@JsonProperty("name") TaxonName name, @JsonProperty("taxonId") long taxonId) {
		this.name = name;
		this.taxonId = taxonId;

	}

	@Override
	public long getTaxonId() {
		return taxonId;
	}

	@Override
	public TaxonName getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (taxonId ^ (taxonId >>> 32));
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
		TaxonImpl other = (TaxonImpl) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (taxonId != other.taxonId)
			return false;
		return true;
	}

}
