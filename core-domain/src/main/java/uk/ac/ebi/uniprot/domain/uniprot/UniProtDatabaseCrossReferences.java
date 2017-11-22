package uk.ac.ebi.uniprot.domain.uniprot;


import uk.ac.ebi.uniprot.domain.uniprot.xdb.DatabaseType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDatabaseCrossReference;

import java.util.List;

public interface UniProtDatabaseCrossReferences {
    List< UniProtDatabaseCrossReference> getCrossReferences();
    List<UniProtDatabaseCrossReference >getCrossReferencesByType(DatabaseType type);
}
