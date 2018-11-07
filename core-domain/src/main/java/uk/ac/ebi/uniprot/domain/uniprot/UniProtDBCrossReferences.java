package uk.ac.ebi.uniprot.domain.uniprot;


import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType;

public interface UniProtDBCrossReferences {
    List< UniProtDBCrossReference> getCrossReferences();
    List<UniProtDBCrossReference >getCrossReferencesByType(UniProtXDbType type);
    List<UniProtDBCrossReference >getCrossReferencesByType(String dbName);
}
