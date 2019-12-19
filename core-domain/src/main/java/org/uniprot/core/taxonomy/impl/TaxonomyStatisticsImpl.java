package org.uniprot.core.taxonomy.impl;

import java.util.Objects;

import org.uniprot.core.taxonomy.TaxonomyStatistics;

public class TaxonomyStatisticsImpl implements TaxonomyStatistics {

    private static final long serialVersionUID = -7521540368502386079L;
    private long reviewedProteinCount;
    private long unreviewedProteinCount;
    private long referenceProteomeCount;
    private long proteomeCount;

    TaxonomyStatisticsImpl() {
        this(0, 0, 0, 0);
    }

    public TaxonomyStatisticsImpl(
            long reviewedProteinCount,
            long unreviewedProteinCount,
            long referenceProteomeCount,
            long proteomeCount) {
        this.reviewedProteinCount = reviewedProteinCount;
        this.unreviewedProteinCount = unreviewedProteinCount;
        this.referenceProteomeCount = referenceProteomeCount;
        this.proteomeCount = proteomeCount;
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
    public long getProteomeCount() {
        return proteomeCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxonomyStatisticsImpl that = (TaxonomyStatisticsImpl) o;
        return getReviewedProteinCount() == that.getReviewedProteinCount()
                && getUnreviewedProteinCount() == that.getUnreviewedProteinCount()
                && getReferenceProteomeCount() == that.getReferenceProteomeCount()
                && getProteomeCount() == that.getProteomeCount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getReviewedProteinCount(),
                getUnreviewedProteinCount(),
                getReferenceProteomeCount(),
                getProteomeCount());
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
                + ", proteomeCount="
                + proteomeCount
                + '}';
    }
}
