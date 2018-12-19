package uk.ac.ebi.uniprot.domain.uniprot.xdb;

import uk.ac.ebi.uniprot.domain.DBCrossReference;

public interface UniProtDBCrossReference extends DBCrossReference<UniProtXDbType> {
    String getIsoformId();
}
