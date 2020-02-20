package org.uniprot.core.cv.keyword;

import java.io.Serializable;

public interface KeywordGeneOntology extends Serializable {
    String getGoId();

    String getGoTerm();
}
