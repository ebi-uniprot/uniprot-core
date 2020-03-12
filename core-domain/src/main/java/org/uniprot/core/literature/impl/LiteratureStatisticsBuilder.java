package org.uniprot.core.literature.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.literature.LiteratureStatistics;

/** @author lgonzales */
public class LiteratureStatisticsBuilder implements Builder<LiteratureStatistics> {

    private long reviewedProteinCount;
    private long unreviewedProteinCount;
    private long mappedProteinCount;

    public @Nonnull LiteratureStatisticsBuilder reviewedProteinCount(long reviewedProteinCount) {
        this.reviewedProteinCount = reviewedProteinCount;
        return this;
    }

    public @Nonnull LiteratureStatisticsBuilder unreviewedProteinCount(
            long unreviewedProteinCount) {
        this.unreviewedProteinCount = unreviewedProteinCount;
        return this;
    }

    public @Nonnull LiteratureStatisticsBuilder mappedProteinCount(long mappedProteinCount) {
        this.mappedProteinCount = mappedProteinCount;
        return this;
    }

    @Override
    public @Nonnull LiteratureStatistics build() {
        return new LiteratureStatisticsImpl(
                reviewedProteinCount, unreviewedProteinCount, mappedProteinCount);
    }

    public static @Nonnull LiteratureStatisticsBuilder from(
            @Nonnull LiteratureStatistics instance) {
        return new LiteratureStatisticsBuilder()
                .reviewedProteinCount(instance.getReviewedProteinCount())
                .unreviewedProteinCount(instance.getUnreviewedProteinCount())
                .mappedProteinCount(instance.getMappedProteinCount());
    }
}