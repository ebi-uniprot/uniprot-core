package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.Value;

public interface InternalLine extends Value {
     InternalLineType getType();
}
