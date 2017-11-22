package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.CitationType;

import java.util.List;

interface UniProtReferences {
    List< UniProtReference<? extends Citation > > getReferences();
    List< UniProtReference<? extends Citation > >getReferencesByType(CitationType type);
}
