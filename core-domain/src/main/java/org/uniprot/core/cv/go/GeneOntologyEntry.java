package org.uniprot.core.cv.go;

import java.io.Serializable;
import java.util.Set;

public interface GeneOntologyEntry extends Serializable, Comparable<GeneOntologyEntry> {
    String getId();

    String getName();

    GoAspect getAspect();

    Set<GeneOntologyEntry> getAncestors();
}
