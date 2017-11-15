package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.common.Value;

public interface EvidencedValue extends Value, HasEvidences{
    String getDisplayedValue(String separator);
}
