package uk.ac.ebi.uniprot.domain.proteome;

import uk.ac.ebi.uniprot.domain.EntryId;

public interface ProteomeId  extends EntryId{
	boolean isValidId();
}
