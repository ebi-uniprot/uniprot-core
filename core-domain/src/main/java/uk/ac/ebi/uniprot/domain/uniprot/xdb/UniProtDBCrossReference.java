package uk.ac.ebi.uniprot.domain.uniprot.xdb;

import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;
import uk.ac.ebi.uniprot.domain.xdb.DBCrossReference;

public interface UniProtDBCrossReference extends DBCrossReference, HasEvidences {
	String getIsoformId();
}
