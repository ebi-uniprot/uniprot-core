package org.uniprot.core.uniprot.comment;

import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprot.evidence.HasEvidences;

public interface Cofactor extends HasEvidences {
    String getName();

    CrossReference<CofactorDatabase> getCofactorReference();

    boolean hasName();

    boolean hasCofactorReference();
}
