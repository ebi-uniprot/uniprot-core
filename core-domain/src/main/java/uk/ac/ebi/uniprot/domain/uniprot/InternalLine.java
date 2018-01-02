package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.common.Value;

public interface InternalLine extends Value {
     InternalLineType getInternalLineType();
}
