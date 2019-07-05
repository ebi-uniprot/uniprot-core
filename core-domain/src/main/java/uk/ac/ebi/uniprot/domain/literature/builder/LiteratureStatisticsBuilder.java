package uk.ac.ebi.uniprot.domain.literature.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.literature.LiteratureStatistics;
import uk.ac.ebi.uniprot.domain.literature.impl.LiteratureStatisticsImpl;

/**
 * @author lgonzales
 */
public class LiteratureStatisticsBuilder implements Builder<LiteratureStatisticsBuilder, LiteratureStatistics> {

    private long reviewedProteinCount;
    private long unreviewedProteinCount;
    private long mappedProteinCount;

    public LiteratureStatisticsBuilder reviewedProteinCount(long reviewedProteinCount) {
        this.reviewedProteinCount = reviewedProteinCount;
        return this;
    }

    public LiteratureStatisticsBuilder unreviewedProteinCount(long unreviewedProteinCount) {
        this.unreviewedProteinCount = unreviewedProteinCount;
        return this;
    }

    public LiteratureStatisticsBuilder mappedProteinCount(long mappedProteinCount) {
        this.mappedProteinCount = mappedProteinCount;
        return this;
    }

    @Override
    public LiteratureStatistics build() {
        return new LiteratureStatisticsImpl(reviewedProteinCount, unreviewedProteinCount, mappedProteinCount);
    }

    @Override
    public LiteratureStatisticsBuilder from(LiteratureStatistics instance) {
        return new LiteratureStatisticsBuilder()
                .reviewedProteinCount(instance.getReviewedProteinCount())
                .unreviewedProteinCount(instance.getUnreviewedProteinCount())
                .mappedProteinCount(instance.getMappedProteinCount());
    }
}
