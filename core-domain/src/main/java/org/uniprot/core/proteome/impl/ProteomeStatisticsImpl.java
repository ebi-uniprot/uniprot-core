package org.uniprot.core.proteome.impl;

import java.util.Objects;

import org.uniprot.core.proteome.ProteomeStatistics;

public class ProteomeStatisticsImpl implements ProteomeStatistics {
    private final long reviewedProteinCount;
    private final long unreviewedProteinCount;
    private final long isoformProteinCount;

    public ProteomeStatisticsImpl(
            long reviewedProteinCount, long unreviewedProteinCount, long isoformProteinCount) {
        this.reviewedProteinCount = reviewedProteinCount;
        this.unreviewedProteinCount = unreviewedProteinCount;
        this.isoformProteinCount = isoformProteinCount;
    }

    @Override
    public long getReviewedProteinCount() {
        return reviewedProteinCount;
    }

    @Override
    public long getUnreviewedProteinCount() {
        return unreviewedProteinCount;
    }

    @Override
    public long getIsoformProteinCount() {
        return isoformProteinCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProteomeStatisticsImpl that = (ProteomeStatisticsImpl) o;
        return reviewedProteinCount == that.reviewedProteinCount
                && unreviewedProteinCount == that.unreviewedProteinCount
                && isoformProteinCount == that.isoformProteinCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewedProteinCount, unreviewedProteinCount, isoformProteinCount);
    }
}
