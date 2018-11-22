package uk.ac.ebi.uniprot.domain.uniprot.impl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtTaxonId;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.util.Utils;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UniProtTaxonIdImpl implements UniProtTaxonId {
    private final long taxonId;
    private final List<Evidence> evidences;
	@JsonCreator
    public UniProtTaxonIdImpl(@JsonProperty("taxonId") long taxonId,
    		@JsonProperty("evidences")List<Evidence> evidences) {
        this.taxonId = taxonId;
        this.evidences = Utils.unmodifierList(evidences);
       ;
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
