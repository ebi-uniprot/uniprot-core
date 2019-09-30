package org.uniprot.core.uniprot.comment;

import org.uniprot.core.uniprot.evidence.HasEvidences;

public interface Absorption extends HasEvidences {
    int getMax();

    boolean isApproximate();

    Note getNote();

    boolean hasMax();

    boolean hasNote();
}
