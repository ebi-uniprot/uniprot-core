package uk.ac.ebi.uniprot.domain.uniprot.xdb;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;

public interface UniProtDBCrossReference extends DBCrossReference, HasEvidences {
	String getIsoformId();
}
