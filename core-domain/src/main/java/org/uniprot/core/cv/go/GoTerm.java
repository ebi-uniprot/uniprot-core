package org.uniprot.core.cv.go;

import java.io.Serializable;

// TODO: 30/11/2020 rename this to GOTerm, not GoTerm, because GO represents Gene Ontology, and
// following Java's naming conventions, a capital letter should be used for each new word. E.g., see
// how
// QuickGO does it.
public interface GoTerm extends Serializable {
    String getId();

    String getName();
}
