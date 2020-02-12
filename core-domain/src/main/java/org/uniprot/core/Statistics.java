package org.uniprot.core;

import java.io.Serializable;

public interface Statistics extends Serializable {
    long getReviewedProteinCount();

    long getUnreviewedProteinCount();

    default boolean hasReviewedProteinCount() {
        return getReviewedProteinCount() > 0;
    }

    default boolean hasUnreviewedProteinCount() {
        return getUnreviewedProteinCount() > 0;
    }
}
