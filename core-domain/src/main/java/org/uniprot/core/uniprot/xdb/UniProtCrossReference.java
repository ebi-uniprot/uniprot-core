package org.uniprot.core.uniprot.xdb;

import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprot.evidence.HasEvidences;

public interface UniProtCrossReference extends CrossReference<UniProtDatabase>, HasEvidences {
    String getIsoformId();

    boolean hasIsoformId();
}
