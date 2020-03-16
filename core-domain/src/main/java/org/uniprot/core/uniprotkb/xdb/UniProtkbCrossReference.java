package org.uniprot.core.uniprotkb.xdb;

import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprotkb.evidence.HasEvidences;

public interface UniProtkbCrossReference extends CrossReference<UniProtkbDatabase>, HasEvidences {
    String getIsoformId();

    boolean hasIsoformId();
}
