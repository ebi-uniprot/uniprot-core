package uk.ac.ebi.uniprot.domain.taxonomy.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismName;

public class OrganismImpl implements Organism {
	private final OrganismName name;
	private final long taxonId;

	@JsonCreator
	public OrganismImpl(@JsonProperty("name") OrganismName name, @JsonProperty("taxonId") long taxonId) {
		this.name = name;
		this.taxonId = taxonId;
	}

	@Override
	public long getTaxonId() {
		return taxonId;
	}

	@Override
	public OrganismName getName() {
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
		OrganismImpl other = (OrganismImpl) obj;
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
