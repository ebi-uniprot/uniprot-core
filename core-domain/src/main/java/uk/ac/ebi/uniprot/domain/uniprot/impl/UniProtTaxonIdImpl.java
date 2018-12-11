package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtTaxonId;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UniProtTaxonIdImpl implements UniProtTaxonId {
    private long taxonId;
    private List<Evidence> evidences;

    private UniProtTaxonIdImpl(){
		this.evidences = Collections.emptyList();
	}

    public UniProtTaxonIdImpl(long taxonId, List<Evidence> evidences) {
        this.taxonId = taxonId;
        this.evidences = Utils.unmodifierList(evidences);
    }

    @Override
    public long getTaxonId() {
        return taxonId;
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
		UniProtTaxonIdImpl other = (UniProtTaxonIdImpl) obj;
		if (evidences == null) {
			if (other.evidences != null)
				return false;
		} else if (!evidences.equals(other.evidences))
			return false;
		if (taxonId != other.taxonId)
			return false;
		return true;
	}

}
