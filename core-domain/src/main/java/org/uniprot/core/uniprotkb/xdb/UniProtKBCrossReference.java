package org.uniprot.core.uniprotkb.xdb;

import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprotkb.evidence.HasEvidences;

public interface UniProtKBCrossReference extends CrossReference<UniProtKBDatabase>, HasEvidences {
    String getIsoformId();

    boolean hasIsoformId();
}
