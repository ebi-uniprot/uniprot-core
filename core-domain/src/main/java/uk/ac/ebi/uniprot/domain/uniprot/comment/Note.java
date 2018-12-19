package uk.ac.ebi.uniprot.domain.uniprot.comment;

import uk.ac.ebi.uniprot.domain.uniprot.FreeText;

public interface Note extends FreeText {
    boolean isValid();

}
