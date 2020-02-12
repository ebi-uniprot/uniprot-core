package org.uniprot.core.taxonomy.impl;

import java.util.Objects;

import org.uniprot.core.impl.StatisticsImpl;
import org.uniprot.core.taxonomy.TaxonomyStatistics;

public class TaxonomyStatisticsImpl extends StatisticsImpl implements TaxonomyStatistics {

    private static final long serialVersionUID = -7521540368502386079L;
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
        super(reviewedProteinCount, unreviewedProteinCount);
        this.referenceProteomeCount = referenceProteomeCount;
        this.proteomeCount = proteomeCount;
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
        return super.equals(that)
                && getReferenceProteomeCount() == that.getReferenceProteomeCount()
                && getProteomeCount() == that.getProteomeCount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getReferenceProteomeCount(), getProteomeCount());
    }

    @Override
    public String toString() {
        return "TaxonomyStatisticsImpl{"
                + "reviewedProteinCount="
                + getReviewedProteinCount()
                + ", unreviewedProteinCount="
                + getUnreviewedProteinCount()
                + ", referenceProteomeCount="
                + referenceProteomeCount
                + ", proteomeCount="
                + proteomeCount
                + '}';
    }
}
