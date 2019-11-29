package org.uniprot.core.taxonomy.builder;

import org.uniprot.core.Builder;
import org.uniprot.core.taxonomy.TaxonomyStatistics;
import org.uniprot.core.taxonomy.impl.TaxonomyStatisticsImpl;

import javax.annotation.Nonnull;

public class TaxonomyStatisticsBuilder
        implements Builder<TaxonomyStatisticsBuilder, TaxonomyStatistics> {

    private long reviewedProteinCount;
    private long unreviewedProteinCount;
    private long referenceProteomeCount;
    private long completeProteomeCount;

    public @Nonnull TaxonomyStatisticsBuilder reviewedProteinCount(long reviewedProteinCount) {
        this.reviewedProteinCount = reviewedProteinCount;
        return this;
    }

    public @Nonnull TaxonomyStatisticsBuilder unreviewedProteinCount(long unreviewedProteinCount) {
        this.unreviewedProteinCount = unreviewedProteinCount;
        return this;
    }

    public @Nonnull TaxonomyStatisticsBuilder referenceProteomeCount(long referenceProteomeCount) {
        this.referenceProteomeCount = referenceProteomeCount;
        return this;
    }

    public @Nonnull TaxonomyStatisticsBuilder completeProteomeCount(long completeProteomeCount) {
        this.completeProteomeCount = completeProteomeCount;
        return this;
    }

    @Override
    public @Nonnull TaxonomyStatistics build() {
        return new TaxonomyStatisticsImpl(
                reviewedProteinCount,
                unreviewedProteinCount,
                referenceProteomeCount,
                completeProteomeCount);
    }

    @Override
    public @Nonnull TaxonomyStatisticsBuilder from(@Nonnull TaxonomyStatistics instance) {
        if (instance != null) {
            this.reviewedProteinCount(instance.getReviewedProteinCount());
            this.unreviewedProteinCount(instance.getUnreviewedProteinCount());
            this.completeProteomeCount(instance.getCompleteProteomeCount());
            this.referenceProteomeCount(instance.getReferenceProteomeCount());
        }
        return this;
    }
}
