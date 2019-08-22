package org.uniprot.core.cv.subcell;

/**
 * @author lgonzales
 * @since 2019-07-11
 */
public interface SubcellularLocationStatistics {

    long getReviewedProteinCount();

    long getUnreviewedProteinCount();
}