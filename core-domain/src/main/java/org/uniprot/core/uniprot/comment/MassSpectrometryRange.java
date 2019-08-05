package org.uniprot.core.uniprot.comment;

import java.io.Serializable;

import org.uniprot.core.Range;

public interface MassSpectrometryRange extends Serializable {
    Range getRange();

    boolean hasIsoformId();

    String getIsoformId();
}
