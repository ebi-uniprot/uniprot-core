package org.uniprot.core.literature.impl;

import java.util.Objects;

import org.uniprot.core.literature.LiteratureStatistics;

/** @author lgonzales */
public class LiteratureStatisticsImpl implements LiteratureStatistics {

    private long reviewedProteinCount;
    private long unreviewedProteinCount;
    private long mappedProteinCount;

    // no arg constructor for JSON deserialization
    LiteratureStatisticsImpl() {
        this(0, 0, 0);
    }

    public LiteratureStatisticsImpl(
            long reviewedProteinCount, long unreviewedProteinCount, long mappedProteinCount) {
        this.reviewedProteinCount = reviewedProteinCount;
        this.unreviewedProteinCount = unreviewedProteinCount;
        this.mappedProteinCount = mappedProteinCount;
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
    public long getMappedProteinCount() {
        return mappedProteinCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiteratureStatisticsImpl that = (LiteratureStatisticsImpl) o;
        return getReviewedProteinCount() == that.getReviewedProteinCount()
                && getUnreviewedProteinCount() == that.getUnreviewedProteinCount()
                && getMappedProteinCount() == that.getMappedProteinCount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getReviewedProteinCount(), getUnreviewedProteinCount(), getMappedProteinCount());
    }

    @Override
    public String toString() {
        return "LiteratureStatisticsImpl{"
                + "reviewedProteinCount="
                + reviewedProteinCount
                + ", unreviewedProteinCount="
                + unreviewedProteinCount
                + ", mappedProteinCount="
                + mappedProteinCount
                + '}';
    }
}
