package org.uniprot.core.cv.keyword.impl;

import java.util.Objects;

import org.uniprot.core.cv.keyword.KeywordStatistics;

/**
 * @author lgonzales
 */
public class KeywordStatisticsImpl implements KeywordStatistics {

    private long reviewedProteinCount;
    private long unreviewedProteinCount;

    private KeywordStatisticsImpl() {
        this(0L, 0L);
    }

    public KeywordStatisticsImpl(long reviewedProteinCount, long unreviewedProteinCount) {
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
        KeywordStatisticsImpl that = (KeywordStatisticsImpl) o;
        return getReviewedProteinCount() == that.getReviewedProteinCount() &&
                getUnreviewedProteinCount() == that.getUnreviewedProteinCount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReviewedProteinCount(), getUnreviewedProteinCount());
    }
}
