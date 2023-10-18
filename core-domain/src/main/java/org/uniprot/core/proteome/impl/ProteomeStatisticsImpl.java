package org.uniprot.core.proteome.impl;

import java.util.Objects;

import org.uniprot.core.impl.StatisticsImpl;
import org.uniprot.core.proteome.ProteomeStatistics;

public class ProteomeStatisticsImpl extends StatisticsImpl implements ProteomeStatistics {
    private static final long serialVersionUID = -8585233468042758658L;
    private final long isoformProteinCount;

    ProteomeStatisticsImpl(
            long reviewedProteinCount, long unreviewedProteinCount, long isoformProteinCount) {
        super(reviewedProteinCount, unreviewedProteinCount);
        this.isoformProteinCount = isoformProteinCount;
    }

    ProteomeStatisticsImpl() {
        this(0, 0, 0);
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
        return getReviewedProteinCount() == that.getReviewedProteinCount()
                && getUnreviewedProteinCount() == that.getUnreviewedProteinCount()
                && isoformProteinCount == that.isoformProteinCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getReviewedProteinCount(), getUnreviewedProteinCount(), isoformProteinCount);
    }
}
