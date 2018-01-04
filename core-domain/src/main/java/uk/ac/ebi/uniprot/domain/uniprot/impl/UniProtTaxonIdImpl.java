package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtTaxonId;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;

import java.util.Collections;
import java.util.List;

public class UniProtTaxonIdImpl implements UniProtTaxonId {
    private final long taxId;
    private final List<Evidence> evidences;

    public UniProtTaxonIdImpl(long taxId, List<Evidence> evidences) {
        this.taxId = taxId;

        if ((evidences != null) && !evidences.isEmpty())
            this.evidences = Collections.unmodifiableList(evidences);
        else
            this.evidences = Collections.emptyList();
    }

    @Override
    public long getTaxonId() {
        return taxId;
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
        result = prime * result + (int) (taxId ^ (taxId >>> 32));
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
        if (taxId != other.taxId)
            return false;
        return true;
    }

}
