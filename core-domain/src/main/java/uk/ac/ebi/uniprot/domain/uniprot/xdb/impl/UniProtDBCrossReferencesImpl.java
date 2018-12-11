package uk.ac.ebi.uniprot.domain.uniprot.xdb.impl;

import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReferences;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UniProtDBCrossReferencesImpl implements UniProtDBCrossReferences {
    private List<UniProtDBCrossReference> crossReferences;

    private UniProtDBCrossReferencesImpl(){
        this.crossReferences = Collections.emptyList();
    }

    public UniProtDBCrossReferencesImpl(List<UniProtDBCrossReferenceImpl> xrefs) {
        if ((xrefs == null) || xrefs.isEmpty()) {
            this.crossReferences = Collections.emptyList();
        } else {
            this.crossReferences = Collections.unmodifiableList(xrefs);
        }
    }

    @Override
    public List<UniProtDBCrossReference> getCrossReferences() {
        return this.crossReferences;
    }

    @Override
    public List<UniProtDBCrossReference> getCrossReferencesByType(UniProtXDbType type) {
    	return getCrossReferencesByType(type.getName());
    }

    @Override
	public List<UniProtDBCrossReference> getCrossReferencesByType(String dbName) {
    	 return this.crossReferences.stream().filter(val -> dbName.equals(val.getDatabaseType().getName())).collect(Collectors.toList());
	}
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((crossReferences == null) ? 0 : crossReferences.hashCode());
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
        if (crossReferences == null) {
            if (other.crossReferences != null)
                return false;
        } else if (!crossReferences.equals(other.crossReferences))
            return false;
        return true;
    }

	

}
