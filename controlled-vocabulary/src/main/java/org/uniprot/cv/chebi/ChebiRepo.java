package org.uniprot.cv.chebi;

import org.uniprot.core.cv.chebi.Chebi;

/**
 * Created 05/06/19
 *
 * @author Edd
 */
public interface ChebiRepo {
    Chebi getById(String id);
}
