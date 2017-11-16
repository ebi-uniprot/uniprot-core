package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.CitationXref;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CitationXrefsBuilder {
    public static CitationXrefs createCitationXrefs(List<CitationXref> xrefs){
        return new CitationXrefsImpl(xrefs);
    }
    
    
    public static CitationXref createCitationXref(CitationXrefType type, String id){
        return new CitationXrefImpl(type, id);
    }
    
    
    static class CitationXrefsImpl implements CitationXrefs {
        private final List<CitationXref> xrefs;

        public CitationXrefsImpl(List<CitationXref> xrefs) {
            if ((xrefs == null) || xrefs.isEmpty()) {
                this.xrefs = Collections.emptyList();
            } else
                this.xrefs = Collections.unmodifiableList(xrefs);
        }

        @Override
        public List<CitationXref> getAllXrefs() {
            return xrefs;
        }

        @Override
        public Optional<CitationXref> hasXref(CitationXrefType type) {
            return xrefs.stream().filter(xref -> xref.getXrefType() == type)
                    .findAny();
        }

    }

    
    static class CitationXrefImpl implements CitationXref {
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

}
