package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReference;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UniProtReferencesImpl implements UniProtReferences {
    private final List<UniProtReference<Citation>> references;

    public UniProtReferencesImpl(List<UniProtReference<Citation>> references) {
        if ((references == null) || references.isEmpty()) {
            this.references = Collections.emptyList();
        } else {
            this.references = Collections.unmodifiableList(references);
        }
    }

    @Override
    public List<UniProtReference<Citation>> getReferences() {
        return references;
    }

    @Override
    public List<UniProtReference<? extends Citation>> getReferencesByType(CitationType type) {
        List<UniProtReference<? extends Citation>> typedReferences = new ArrayList<>();
        for (UniProtReference<Citation> reference : references) {
            if (reference.getCitation().getCitationType() == type) {
                typedReferences.add(reference);
            }
        }
        return typedReferences;
    }

}
