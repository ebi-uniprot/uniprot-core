package org.uniprot.core.taxonomy.impl;

import java.util.Objects;

import org.uniprot.core.taxonomy.TaxonomyStatistics;

public class TaxonomyStatisticsImpl implements TaxonomyStatistics {

    private long reviewedProteinCount;
    private long unreviewedProteinCount;
    private long referenceProteomeCount;
    private long completeProteomeCount;

    private TaxonomyStatisticsImpl() {
        this(0, 0, 0, 0);
    }

    public TaxonomyStatisticsImpl(
            long reviewedProteinCount,
            long unreviewedProteinCount,
            long referenceProteomeCount,
            long completeProteomeCount) {
        this.reviewedProteinCount = reviewedProteinCount;
        this.unreviewedProteinCount = unreviewedProteinCount;
        this.referenceProteomeCount = referenceProteomeCount;
        this.completeProteomeCount = completeProteomeCount;
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
    public long getReferenceProteomeCount() {
        return referenceProteomeCount;
    }

    @Override
    public long getCompleteProteomeCount() {
        return completeProteomeCount;
    }

    @Override
    public boolean hasReviewedProteinCount() {
        return reviewedProteinCount > 0L;
    }

    @Override
    public boolean hasUnreviewedProteinCount() {
        return unreviewedProteinCount > 0L;
    }

    @Override
    public boolean hasReferenceProteomeCount() {
        return referenceProteomeCount > 0L;
    }

    @Override
    public boolean hasCompleteProteomeCount() {
        return completeProteomeCount > 0L;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxonomyStatisticsImpl that = (TaxonomyStatisticsImpl) o;
        return getReviewedProteinCount() == that.getReviewedProteinCount()
                && getUnreviewedProteinCount() == that.getUnreviewedProteinCount()
                && getReferenceProteomeCount() == that.getReferenceProteomeCount()
                && getCompleteProteomeCount() == that.getCompleteProteomeCount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getReviewedProteinCount(),
                getUnreviewedProteinCount(),
                getReferenceProteomeCount(),
                getCompleteProteomeCount());
    }

    @Override
    public String toString() {
        return "TaxonomyStatisticsImpl{"
                + "reviewedProteinCount="
                + reviewedProteinCount
                + ", unreviewedProteinCount="
                + unreviewedProteinCount
                + ", referenceProteomeCount="
                + referenceProteomeCount
                + ", completeProteomeCount="
                + completeProteomeCount
                + '}';
    }
}
