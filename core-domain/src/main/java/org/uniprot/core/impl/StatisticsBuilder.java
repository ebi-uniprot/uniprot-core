package org.uniprot.core.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.Statistics;

import javax.annotation.Nonnull;

public class StatisticsBuilder implements Builder<Statistics> {
    private long reviewedProteinCount;
    private long unreviewedProteinCount;

    public @Nonnull StatisticsBuilder reviewedProteinCount(long reviewedProteinCount) {
        this.reviewedProteinCount = reviewedProteinCount;
        return this;
    }

    public @Nonnull StatisticsBuilder unreviewedProteinCount(long unreviewedProteinCount) {
        this.unreviewedProteinCount = unreviewedProteinCount;
        return this;
    }

    public @Nonnull Statistics build() {
        return new StatisticsImpl(reviewedProteinCount, unreviewedProteinCount);
    }

    public static @Nonnull StatisticsBuilder from(@Nonnull Statistics instance) {
        return new StatisticsBuilder()
                .reviewedProteinCount(instance.getReviewedProteinCount())
                .unreviewedProteinCount(instance.getUnreviewedProteinCount());
    }
}
