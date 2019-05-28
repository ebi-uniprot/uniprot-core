package uk.ac.ebi.uniprot.domain.uniparc;

import uk.ac.ebi.uniprot.domain.EntryId;

/**
 *
 * @author jluo
 * @date: 21 May 2019
 *
*/

public interface UniParcId extends EntryId {
	boolean isValidId();
}

