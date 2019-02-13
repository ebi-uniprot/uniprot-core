package uk.ac.ebi.uniprot.domain.uniprot.xdb;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.HasEvidences;

public interface UniProtDBCrossReference extends DBCrossReference<UniProtXDbType>, HasEvidences {
    String getIsoformId();

    boolean hasIsoformId();
}
