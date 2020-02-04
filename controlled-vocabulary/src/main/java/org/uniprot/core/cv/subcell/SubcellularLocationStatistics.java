package org.uniprot.core.cv.subcell;

import java.io.Serializable;

/**
 * @author lgonzales
 * @since 2019-07-11
 */
public interface SubcellularLocationStatistics extends Serializable {

    long getReviewedProteinCount();

    long getUnreviewedProteinCount();
}
