package org.uniprot.core.taxonomy.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.taxonomy.TaxonomyStatistics;

public class TaxonomyStatisticsBuilder implements Builder<TaxonomyStatistics> {

    private long reviewedProteinCount;
    private long unreviewedProteinCount;
    private long referenceProteomeCount;
    private long proteomeCount;

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

    public @Nonnull TaxonomyStatisticsBuilder proteomeCount(long proteomeCount) {
        this.proteomeCount = proteomeCount;
        return this;
    }

    @Override
    public @Nonnull TaxonomyStatistics build() {
        return new TaxonomyStatisticsImpl(
                reviewedProteinCount,
                unreviewedProteinCount,
                referenceProteomeCount,
                proteomeCount);
    }

    public static @Nonnull TaxonomyStatisticsBuilder from(@Nonnull TaxonomyStatistics instance) {
        return new TaxonomyStatisticsBuilder()
                .reviewedProteinCount(instance.getReviewedProteinCount())
                .unreviewedProteinCount(instance.getUnreviewedProteinCount())
                .proteomeCount(instance.getProteomeCount())
                .referenceProteomeCount(instance.getReferenceProteomeCount());
    }
}
