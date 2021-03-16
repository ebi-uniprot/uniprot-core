package org.uniprot.core.literature.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.literature.LiteratureStatistics;

/** @author lgonzales */
public class LiteratureStatisticsBuilder implements Builder<LiteratureStatistics> {

    private long reviewedProteinCount;
    private long unreviewedProteinCount;
    private long computationallyMappedProteinCount;
    private long communityMappedProteinCount;

    public @Nonnull LiteratureStatisticsBuilder reviewedProteinCount(long reviewedProteinCount) {
        this.reviewedProteinCount = reviewedProteinCount;
        return this;
    }

    public @Nonnull LiteratureStatisticsBuilder unreviewedProteinCount(
            long unreviewedProteinCount) {
        this.unreviewedProteinCount = unreviewedProteinCount;
        return this;
    }

    public @Nonnull LiteratureStatisticsBuilder computationallyMappedProteinCount(
            long computationallyMappedProteinCount) {
        this.computationallyMappedProteinCount = computationallyMappedProteinCount;
        return this;
    }

    public @Nonnull LiteratureStatisticsBuilder communityMappedProteinCount(
            long communityMappedProteinCount) {
        this.communityMappedProteinCount = communityMappedProteinCount;
        return this;
    }

    @Override
    public @Nonnull LiteratureStatistics build() {
        return new LiteratureStatisticsImpl(
                reviewedProteinCount,
                unreviewedProteinCount,
                computationallyMappedProteinCount,
                communityMappedProteinCount);
    }

    public static @Nonnull LiteratureStatisticsBuilder from(
            @Nonnull LiteratureStatistics instance) {
        return new LiteratureStatisticsBuilder()
                .reviewedProteinCount(instance.getReviewedProteinCount())
                .unreviewedProteinCount(instance.getUnreviewedProteinCount())
                .computationallyMappedProteinCount(instance.getComputationallyMappedProteinCount())
                .communityMappedProteinCount(instance.getCommunityMappedProteinCount());
    }
}
