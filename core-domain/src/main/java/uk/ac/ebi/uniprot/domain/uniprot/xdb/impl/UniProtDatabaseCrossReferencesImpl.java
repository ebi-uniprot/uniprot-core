package uk.ac.ebi.uniprot.domain.uniprot.xdb.impl;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtDatabaseCrossReferences;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.DatabaseType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDatabaseCrossReference;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UniProtDatabaseCrossReferencesImpl implements UniProtDatabaseCrossReferences {
    private final List<UniProtDatabaseCrossReference> xrefs;

    public UniProtDatabaseCrossReferencesImpl(List<UniProtDatabaseCrossReference> xrefs) {
        if ((xrefs == null) || xrefs.isEmpty()) {
            this.xrefs = Collections.emptyList();
        } else {
            this.xrefs = Collections.unmodifiableList(xrefs);
        }
    }

    @Override
    public List<UniProtDatabaseCrossReference> getCrossReferences() {
        return this.xrefs;
    }

    @Override
    public List<UniProtDatabaseCrossReference> getCrossReferencesByType(DatabaseType type) {
        return this.xrefs.stream().filter(val -> (val.getDatabase() == type)).collect(Collectors.toList());
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
        UniProtDatabaseCrossReferencesImpl other = (UniProtDatabaseCrossReferencesImpl) obj;
        if (xrefs == null) {
            if (other.xrefs != null)
                return false;
        } else if (!xrefs.equals(other.xrefs))
            return false;
        return true;
    }

}
