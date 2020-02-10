package org.uniprot.core.cv.keyword;

import java.io.Serializable;

public interface GeneOntology extends Serializable {
    String getGoId();

    String getGoTerm();
}
