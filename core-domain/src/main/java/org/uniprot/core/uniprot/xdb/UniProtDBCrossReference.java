package org.uniprot.core.uniprot.xdb;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.uniprot.evidence.HasEvidences;

public interface UniProtDBCrossReference extends DBCrossReference<UniProtDatabase>, HasEvidences {
    String getIsoformId();

    boolean hasIsoformId();
}
