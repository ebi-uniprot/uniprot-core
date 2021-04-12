package org.uniprot.core.interpro;

import org.uniprot.core.EntryId;

/**
 * The id of an InterPro Entry.
 */
public interface InterProAc extends EntryId {
	boolean isValidId();
}
