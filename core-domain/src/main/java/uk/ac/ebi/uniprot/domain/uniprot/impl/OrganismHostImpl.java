package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismHost;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonId;

public class OrganismHostImpl  implements OrganismHost {
	private final TaxonId taxonId;
	private final Organism organism;
	  public OrganismHostImpl(long taxonId, Organism organism) {
		
		  this.taxonId = new TaxonIdImpl(taxonId);
		  this.organism =organism;
	}
	@Override
	public TaxonId getTaxonId() {
		return taxonId;
	}
	@Override
	public Organism getOrganism() {
		return organism;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((organism == null) ? 0 : organism.hashCode());
		result = prime * result + ((taxonId == null) ? 0 : taxonId.hashCode());
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
		OrganismHostImpl other = (OrganismHostImpl) obj;
		if (organism == null) {
			if (other.organism != null)
				return false;
		} else if (!organism.equals(other.organism))
			return false;
		if (taxonId == null) {
			if (other.taxonId != null)
				return false;
		} else if (!taxonId.equals(other.taxonId))
			return false;
		return true;
	}
	
	
}
