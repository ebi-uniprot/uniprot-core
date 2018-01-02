package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import uk.ac.ebi.uniprot.domain.uniprot.description.Flag;
import uk.ac.ebi.uniprot.domain.uniprot.description.FlagType;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;

import java.util.Collections;
import java.util.List;

public class FlagImpl implements Flag {
    private final FlagType type;
    private final List<Evidence> evidences;
    public FlagImpl(FlagType type, List<Evidence> evidences){
        this.type = type;
        if((evidences !=null) && !evidences.isEmpty())
            this.evidences = Collections.unmodifiableList(evidences);
        else
            this.evidences = Collections.emptyList();
    }
    @Override
    public List<Evidence> getEvidences() {
      return evidences;
    }

    @Override
    public FlagType getFlagType() {
        return type;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((evidences == null) ? 0 : evidences.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        FlagImpl other = (FlagImpl) obj;
        if (evidences == null) {
            if (other.evidences != null)
                return false;
        } else if (!evidences.equals(other.evidences))
            return false;
        if (type != other.type)
            return false;
        return true;
    }

}
