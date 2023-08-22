package org.uniprot.core.proteome.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.proteome.ProteomeStatistics;

import javax.annotation.Nonnull;

public class ProteomeStatisticsBuilder implements Builder<ProteomeStatistics> {

    private long reviewedProteinCount;
    private long unreviewedProteinCount;
    private long isoformProteinCount;

    public @Nonnull ProteomeStatisticsBuilder reviewedProteinCount(long reviewedProteinCount) {
        this.reviewedProteinCount = reviewedProteinCount;
        return this;
    }

    public @Nonnull ProteomeStatisticsBuilder unreviewedProteinCount(long unreviewedProteinCount) {
        this.unreviewedProteinCount = unreviewedProteinCount;
        return this;
    }

    public @Nonnull ProteomeStatisticsBuilder isoformProteinCount(long referenceProteomeCount) {
        this.isoformProteinCount = referenceProteomeCount;
        return this;
    }

    @Override
    public @Nonnull ProteomeStatistics build() {
        return new ProteomeStatisticsImpl(
                reviewedProteinCount,
                unreviewedProteinCount,
                isoformProteinCount);
    }

    public static @Nonnull ProteomeStatisticsBuilder from(@Nonnull ProteomeStatistics instance) {
        return new ProteomeStatisticsBuilder()
                .reviewedProteinCount(instance.getReviewedProteinCount())
                .unreviewedProteinCount(instance.getUnreviewedProteinCount())
                .isoformProteinCount(instance.getIsoformProteinCount());
    }
}
