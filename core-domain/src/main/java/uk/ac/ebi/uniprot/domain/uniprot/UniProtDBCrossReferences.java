package uk.ac.ebi.uniprot.domain.uniprot;


import uk.ac.ebi.uniprot.domain.uniprot.xdb.DatabaseType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;

import java.util.List;

public interface UniProtDBCrossReferences {
    List< UniProtDBCrossReference> getCrossReferences();
    List<UniProtDBCrossReference >getCrossReferencesByType(DatabaseType type);
}
