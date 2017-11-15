package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.taxonomy.TaxonId;

public class TaxonIdImpl implements TaxonId {
    private final long taxonId;
    public TaxonIdImpl(long taxonId){
        this.taxonId = taxonId;
    }
    @Override
    public long getTaxonId() {
        return taxonId;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        TaxonIdImpl other = (TaxonIdImpl) obj;
        if (taxonId != other.taxonId)
            return false;
        return true;
    }

}
