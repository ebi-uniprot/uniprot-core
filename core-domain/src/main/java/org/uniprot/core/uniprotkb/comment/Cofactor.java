package org.uniprot.core.uniprotkb.comment;

import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprotkb.evidence.HasEvidences;

public interface Cofactor extends HasEvidences {
    String getName();

    CrossReference<CofactorDatabase> getCofactorCrossReference();

    boolean hasName();

    boolean hasCofactorCrossReference();
}
