package org.uniprot.core.uniprotkb.comment;

import org.uniprot.core.uniprotkb.evidence.HasEvidences;

public interface Absorption extends HasEvidences {
    int getMax();

    boolean isApproximate();

    Note getNote();

    boolean hasMax();

    boolean hasNote();
}
