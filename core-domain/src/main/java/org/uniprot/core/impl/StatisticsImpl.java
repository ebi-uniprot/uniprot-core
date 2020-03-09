package org.uniprot.core.impl;

import java.util.Objects;

import org.uniprot.core.Statistics;

public class StatisticsImpl implements Statistics {
    private static final long serialVersionUID = 1767966627449407612L;

    private long reviewedProteinCount;
    private long unreviewedProteinCount;

    // no arg constructor for JSON deserialization
    StatisticsImpl() {
        this(0L, 0L);
    }

    protected StatisticsImpl(long reviewedProteinCount, long unreviewedProteinCount) {
        this.reviewedProteinCount = reviewedProteinCount;
        this.unreviewedProteinCount = unreviewedProteinCount;
    }

    public long getReviewedProteinCount() {
        return reviewedProteinCount;
    }

    public long getUnreviewedProteinCount() {
        return unreviewedProteinCount;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistics that = (Statistics) o;
        return getReviewedProteinCount() == that.getReviewedProteinCount()
                && getUnreviewedProteinCount() == that.getUnreviewedProteinCount();
    }

    public int hashCode() {
        return Objects.hash(getReviewedProteinCount(), getUnreviewedProteinCount());
    }
}
