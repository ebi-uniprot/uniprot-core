package uk.ac.ebi.uniprot.domain.uniprot.comment2;

import uk.ac.ebi.uniprot.domain.uniprot.FreeText;

public interface Note extends FreeText {
    boolean isValid();
}
