package uk.ac.ebi.uniprot.domain.uniprot.comment2.impl;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.CofactorReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.Collections;
import java.util.List;

public class CofactorImpl implements Cofactor {

    private String name;
    private List<Evidence> evidences;
    private DBCrossReference<CofactorReferenceType> cofactorReference;

    private CofactorImpl() {
        this.evidences = Collections.emptyList();
    }

    public CofactorImpl(String name,
                        DBCrossReference<CofactorReferenceType> cofactorReference,
                        List<Evidence> evidences) {
        this.name = name;
        if ((evidences == null) || evidences.isEmpty()) {
            this.evidences = Collections.emptyList();
        } else {
            this.evidences = Collections.unmodifiableList(evidences);
        }
        this.cofactorReference = cofactorReference;
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DBCrossReference<CofactorReferenceType> getCofactorReference() {
        return cofactorReference;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((evidences == null) ? 0 : evidences.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((cofactorReference == null) ? 0 : cofactorReference.hashCode());
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
        CofactorImpl other = (CofactorImpl) obj;
        if (evidences == null) {
            if (other.evidences != null)
                return false;
        } else if (!evidences.equals(other.evidences))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (cofactorReference == null) {
            if (other.cofactorReference != null)
                return false;
        } else if (!cofactorReference.equals(other.cofactorReference))
            return false;
        return true;
    }


}
