package org.uniprot.cv.chebi;

import org.uniprot.core.cv.chebi.ChebiEntry;

/**
 * Created 05/06/19
 *
 * @author Edd
 */
public interface ChebiRepo {
    ChebiEntry getById(String id);
}
