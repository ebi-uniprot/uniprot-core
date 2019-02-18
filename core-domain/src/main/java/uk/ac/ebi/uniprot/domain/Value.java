package uk.ac.ebi.uniprot.domain;

import java.io.Serializable;

public interface Value extends Serializable {
    String getValue();

    boolean hasValue();
}
