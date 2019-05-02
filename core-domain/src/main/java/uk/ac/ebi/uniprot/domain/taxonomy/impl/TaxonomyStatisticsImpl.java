package uk.ac.ebi.uniprot.domain.taxonomy.impl;

import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyStatistics;

import java.util.Objects;

public class TaxonomyStatisticsImpl implements TaxonomyStatistics {

    private long reviewedProteinCount;
    private long unreviewedProteinCount;
    private long proteomeCount;

    private TaxonomyStatisticsImpl(){
        this(0,0,0);
    }

    public TaxonomyStatisticsImpl(long reviewedProteinCount, long unreviewedProteinCount, long proteomeCount) {
        this.reviewedProteinCount = reviewedProteinCount;
        this.unreviewedProteinCount = unreviewedProteinCount;
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
    public long getProteomeCount() {
        return proteomeCount;
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
    public boolean hasProteomeCount() {
        return proteomeCount > 0L;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxonomyStatisticsImpl that = (TaxonomyStatisticsImpl) o;
        return getReviewedProteinCount() == that.getReviewedProteinCount() &&
                getUnreviewedProteinCount() == that.getUnreviewedProteinCount() &&
                getProteomeCount() == that.getProteomeCount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReviewedProteinCount(), getUnreviewedProteinCount(), getProteomeCount());
    }

    @Override
    public String toString() {
        return "TaxonomyStatisticsImpl{" +
                "reviewedProteinCount=" + reviewedProteinCount +
                ", unreviewedProteinCount=" + unreviewedProteinCount +
                ", proteomeCount=" + proteomeCount +
                '}';
    }
}
