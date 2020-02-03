package org.uniprot.core.cv.chebi;

import java.io.Serializable;

/**
 * Created 05/06/19
 *
 * @author Edd
 */
public interface Chebi extends Serializable {
    String getId();

    String getName();

    String getInchiKey();
}
