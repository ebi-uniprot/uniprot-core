package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CofactorImpl implements Cofactor {
   
    private final String name;
    private final List<Evidence> evidences;
    private final DBCrossReference<CofactorReferenceType>  cofactorReference;
	@JsonCreator
    public CofactorImpl(@JsonProperty("name") String name, 
    		@JsonProperty("cofactorReference") DBCrossReference<CofactorReferenceType> cofactorReference,
    		@JsonProperty("evidences") List<Evidence> evidences) {
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
    public DBCrossReference<CofactorReferenceType>  getCofactorReference() {
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
