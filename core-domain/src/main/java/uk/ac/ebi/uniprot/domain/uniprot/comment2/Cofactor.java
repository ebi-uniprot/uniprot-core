package uk.ac.ebi.uniprot.domain.uniprot.comment2;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;

public interface Cofactor extends HasEvidences {
    String getName();

    DBCrossReference<CofactorReferenceType> getCofactorReference();
}
