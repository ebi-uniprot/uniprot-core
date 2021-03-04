package org.uniprot.core.literature;

import org.uniprot.core.Statistics;

/** @author lgonzales */
public interface LiteratureStatistics extends Statistics {

    long getComputationallyMappedProteinCount();

    long getCommunityMappedProteinCount();

    default boolean hasComputationallyMappedProteinCount() {
        return getComputationallyMappedProteinCount() > 0;
    }

    default boolean hasCommunityMappedProteinCount() {
        return getCommunityMappedProteinCount() > 0;
    }

    default boolean isLargeScale() {
        return (getComputationallyMappedProteinCount()
                        + getCommunityMappedProteinCount()
                        + getReviewedProteinCount()
                        + getUnreviewedProteinCount())
                > 50;
    }
}
