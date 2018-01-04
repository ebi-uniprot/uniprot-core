package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReference;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UniProtReferencesImpl implements UniProtReferences {
    private final List<UniProtReference<? extends Citation>> references;

    public UniProtReferencesImpl(List<UniProtReference<? extends Citation>> references) {
        if ((references == null) || references.isEmpty()) {
            this.references = Collections.emptyList();
        } else {
            this.references = Collections.unmodifiableList(references);
        }
    }

    @Override
    public List<UniProtReference<? extends Citation>> getReferences() {
        return references;
    }

    @Override
    public List<UniProtReference<? extends Citation>> getReferencesByType(CitationType type) {
        List<UniProtReference<? extends Citation>> typedReferences = new ArrayList<>();
        for (UniProtReference<? extends Citation> reference : references) {
            if (reference.getCitation().getCitationType() == type) {
                typedReferences.add(reference);
            }
        }
        return typedReferences;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((references == null) ? 0 : references.hashCode());
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
        UniProtReferencesImpl other = (UniProtReferencesImpl) obj;
        if (references == null) {
            if (other.references != null)
                return false;
        } else if (!references.equals(other.references))
            return false;
        return true;
    }

}
