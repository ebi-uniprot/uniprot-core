package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.CitationType;

import java.util.List;

public interface UniProtReferences {
    List< UniProtReference<Citation > > getReferences();
    List< UniProtReference<? extends Citation > >getReferencesByType(CitationType type);
}
