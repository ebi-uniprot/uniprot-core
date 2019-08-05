package org.uniprot.core.uniprot.comment;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.uniprot.evidence.HasEvidences;

public interface Cofactor extends HasEvidences {
    String getName();

    DBCrossReference<CofactorReferenceType> getCofactorReference();

    boolean hasName();

    boolean hasCofactorReference();
}
