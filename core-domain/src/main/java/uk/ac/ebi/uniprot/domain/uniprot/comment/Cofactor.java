package uk.ac.ebi.uniprot.domain.uniprot.comment;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.HasEvidences;

public interface Cofactor extends HasEvidences {
    String getName();

    DBCrossReference<CofactorReferenceType> getCofactorReference();

    boolean hasName();

    boolean hasCofactorReference();
}
