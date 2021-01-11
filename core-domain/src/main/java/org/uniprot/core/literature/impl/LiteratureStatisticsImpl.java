package org.uniprot.core.literature.impl;

import java.util.Objects;

import org.uniprot.core.impl.StatisticsImpl;
import org.uniprot.core.literature.LiteratureStatistics;

/** @author lgonzales */
public class LiteratureStatisticsImpl extends StatisticsImpl implements LiteratureStatistics {

    private long computationallyMappedProteinCount;
    private long communityMappedProteinCount;

    // no arg constructor for JSON deserialization
    LiteratureStatisticsImpl() {
        this(0, 0, 0, 0);
    }

    LiteratureStatisticsImpl(
            long reviewedProteinCount, long unreviewedProteinCount, long computationallyMappedProteinCount,
            long communityMappedProteinCount) {
        super(reviewedProteinCount, unreviewedProteinCount);
        this.computationallyMappedProteinCount = computationallyMappedProteinCount;
        this.communityMappedProteinCount = communityMappedProteinCount;
    }

    @Override
    public long getComputationallyMappedProteinCount() {
        return computationallyMappedProteinCount;
    }

    @Override
    public long getCommunityMappedProteinCount() {
        return communityMappedProteinCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiteratureStatisticsImpl that = (LiteratureStatisticsImpl) o;
        return super.equals(that) && getComputationallyMappedProteinCount() == that.getComputationallyMappedProteinCount()
                && getCommunityMappedProteinCount() == that.getCommunityMappedProteinCount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getComputationallyMappedProteinCount(), getCommunityMappedProteinCount());
    }

    @Override
    public String toString() {
        return "LiteratureStatisticsImpl{"
                + "reviewedProteinCount="
                + getReviewedProteinCount()
                + ", unreviewedProteinCount="
                + getUnreviewedProteinCount()
                + ", computationallyMappedProteinCount="
                + computationallyMappedProteinCount
                + ", communityMappedProteinCount="
                + communityMappedProteinCount
                + '}';
    }
}
