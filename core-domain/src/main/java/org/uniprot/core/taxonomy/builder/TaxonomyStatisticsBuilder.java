package org.uniprot.core.taxonomy.builder;

import org.uniprot.core.Builder;
import org.uniprot.core.taxonomy.TaxonomyStatistics;
import org.uniprot.core.taxonomy.impl.TaxonomyStatisticsImpl;

public class TaxonomyStatisticsBuilder
        implements Builder<TaxonomyStatisticsBuilder, TaxonomyStatistics> {

    private long reviewedProteinCount;
    private long unreviewedProteinCount;
    private long referenceProteomeCount;
    private long completeProteomeCount;

    public TaxonomyStatisticsBuilder reviewedProteinCount(long reviewedProteinCount) {
        this.reviewedProteinCount = reviewedProteinCount;
        return this;
    }

    public TaxonomyStatisticsBuilder unreviewedProteinCount(long unreviewedProteinCount) {
        this.unreviewedProteinCount = unreviewedProteinCount;
        return this;
    }

    public TaxonomyStatisticsBuilder referenceProteomeCount(long referenceProteomeCount) {
        this.referenceProteomeCount = referenceProteomeCount;
        return this;
    }

    public TaxonomyStatisticsBuilder completeProteomeCount(long completeProteomeCount) {
        this.completeProteomeCount = completeProteomeCount;
        return this;
    }

    @Override
    public TaxonomyStatistics build() {
        return new TaxonomyStatisticsImpl(
                reviewedProteinCount,
                unreviewedProteinCount,
                referenceProteomeCount,
                completeProteomeCount);
    }

    @Override
    public TaxonomyStatisticsBuilder from(TaxonomyStatistics instance) {
        if (instance != null) {
            this.reviewedProteinCount(instance.getReviewedProteinCount());
            this.unreviewedProteinCount(instance.getUnreviewedProteinCount());
            this.completeProteomeCount(instance.getCompleteProteomeCount());
            this.referenceProteomeCount(instance.getReferenceProteomeCount());
        }
        return this;
    }
}
