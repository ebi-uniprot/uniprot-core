package org.uniprot.core.cv.go;

import java.io.Serializable;
import java.util.Set;

public interface GeneOntologyEntry extends GoTerm, Serializable, Comparable<GeneOntologyEntry> {
    GoAspect getAspect();

    Set<GeneOntologyEntry> getAncestors();
}
