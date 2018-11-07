package uk.ac.ebi.uniprot.domain.uniprot.xdb.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtDBCrossReferences;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType;

public class UniProtDBCrossReferencesImpl implements UniProtDBCrossReferences {
    private final List<UniProtDBCrossReference> xrefs;

    public UniProtDBCrossReferencesImpl(List<UniProtDBCrossReference> xrefs) {
        if ((xrefs == null) || xrefs.isEmpty()) {
            this.xrefs = Collections.emptyList();
        } else {
            this.xrefs = Collections.unmodifiableList(xrefs);
        }
    }

    @Override
    public List<UniProtDBCrossReference> getCrossReferences() {
        return this.xrefs;
    }

    @Override
    public List<UniProtDBCrossReference> getCrossReferencesByType(UniProtXDbType type) {
    	return getCrossReferencesByType(type.getName());
    }

    @Override
	public List<UniProtDBCrossReference> getCrossReferencesByType(String dbName) {
    	 return this.xrefs.stream().filter(val -> dbName.equals(val.getDatabaseName())).collect(Collectors.toList());
	}
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((xrefs == null) ? 0 : xrefs.hashCode());
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
        UniProtDBCrossReferencesImpl other = (UniProtDBCrossReferencesImpl) obj;
        if (xrefs == null) {
            if (other.xrefs != null)
                return false;
        } else if (!xrefs.equals(other.xrefs))
            return false;
        return true;
    }

	

}
