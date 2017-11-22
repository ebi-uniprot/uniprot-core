package uk.ac.ebi.uniprot.domain.uniprot.xdb;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtIsoformId;
import uk.ac.ebi.uniprot.domain.xdb.DatabaseCrossReference;

import java.util.Optional;

public interface UniProtDatabaseCrossReference extends DatabaseCrossReference<DatabaseType> {
    Optional<DbXRefAttribute> getThirdAttribute();
    Optional<DbXRefAttribute> getFourthAttribute();
    Optional<UniProtIsoformId> getIsoformId();
}
