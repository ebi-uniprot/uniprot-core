package org.uniprot.core.literature;

import java.io.Serializable;

/**
 * @author lgonzales
 */
public interface LiteratureStatistics extends Serializable {

    long getReviewedProteinCount();

    long getUnreviewedProteinCount();

    long getMappedProteinCount();

    default boolean hasReviewedProteinCount() {
        return getReviewedProteinCount() > 0;
    }

    default boolean hasUnreviewedProteinCount() {
        return getUnreviewedProteinCount() > 0;
    }

    default boolean hasMappedProteinCount() {
        return getMappedProteinCount() > 0;
    }

}
