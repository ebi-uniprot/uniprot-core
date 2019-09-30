package org.uniprot.core.uniref;

import org.uniprot.core.EntryId;

/**
 * @author jluo
 * @date: 12 Aug 2019
 */
public interface UniRefEntryId extends EntryId {
    boolean isValidId();
}
