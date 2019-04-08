package uk.ac.ebi.uniprot.domain.proteome;

import java.io.Serializable;
import java.util.List;

import uk.ac.ebi.uniprot.domain.DBCrossReference;

public interface Component extends Serializable {
	String getName();
	String getDescription();
	List<DBCrossReference<ProteomeXReferenceType>> getDbXReferences();
	long getProteinCount();
}
