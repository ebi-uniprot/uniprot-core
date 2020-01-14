package org.uniprot.core.taxonomy;

import java.io.Serializable;

public interface TaxonomyStatistics extends Serializable {

    long getReviewedProteinCount();

    long getUnreviewedProteinCount();

    long getReferenceProteomeCount();

    long getProteomeCount();

    default boolean hasReviewedProteinCount() {
        return getReviewedProteinCount() > 0L;
    }

    default boolean hasUnreviewedProteinCount() {
        return getUnreviewedProteinCount() > 0L;
    }

    default boolean hasReferenceProteomeCount() {
        return getReferenceProteomeCount() > 0L;
    }

    default boolean hasProteomeCount() {
        return getProteomeCount() > 0L;
    }
}
