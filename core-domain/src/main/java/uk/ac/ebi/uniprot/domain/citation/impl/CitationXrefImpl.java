package uk.ac.ebi.uniprot.domain.citation.impl;

import uk.ac.ebi.uniprot.domain.citation.CitationXref;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;

public class CitationXrefImpl implements CitationXref {
    private final CitationXrefType type;
    private final String id;
    
    public CitationXrefImpl(CitationXrefType type, String id){
        this.type =type;
        this.id = id;
    }
    @Override
    public CitationXrefType getXrefType() {
        return type;
    }

    @Override
    public String getId() {
        return id;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        CitationXrefImpl other = (CitationXrefImpl) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (type != other.type)
            return false;
        return true;
    }
    @Override
    public String toString() {
        return type.getValue() +"=" + id;
        
    }

}
