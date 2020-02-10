package org.uniprot.cv.subcell.impl;

import java.util.Objects;

import org.uniprot.cv.subcell.SubcellularLocationStatistics;

/**
 * @author lgonzales
 * @since 2019-07-11
 */
public class SubcellularLocationStatisticsImpl implements SubcellularLocationStatistics {

    private static final long serialVersionUID = -2917473627048690554L;
    private long reviewedProteinCount;
    private long unreviewedProteinCount;

    private SubcellularLocationStatisticsImpl() {
        this(0L, 0L);
    }

    public SubcellularLocationStatisticsImpl(
            long reviewedProteinCount, long unreviewedProteinCount) {
        this.reviewedProteinCount = reviewedProteinCount;
        this.unreviewedProteinCount = unreviewedProteinCount;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubcellularLocationStatisticsImpl that = (SubcellularLocationStatisticsImpl) o;
        return getReviewedProteinCount() == that.getReviewedProteinCount()
                && getUnreviewedProteinCount() == that.getUnreviewedProteinCount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReviewedProteinCount(), getUnreviewedProteinCount());
    }
}
