package org.uniprot.core.proteome.impl;

import org.uniprot.core.impl.StatisticsImpl;
import org.uniprot.core.proteome.ProteomeStatistics;

import java.util.Objects;

public class ProteomeStatisticsImpl extends StatisticsImpl implements ProteomeStatistics {
    private final long isoformProteinCount;

    ProteomeStatisticsImpl(long reviewedProteinCount, long unreviewedProteinCount, long isoformProteinCount) {
        super(reviewedProteinCount, unreviewedProteinCount);
        this.isoformProteinCount = isoformProteinCount;
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
        return getReviewedProteinCount() == that.getReviewedProteinCount() && getUnreviewedProteinCount() == that.getUnreviewedProteinCount() && isoformProteinCount == that.isoformProteinCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReviewedProteinCount(), getUnreviewedProteinCount(), isoformProteinCount);
    }
}
