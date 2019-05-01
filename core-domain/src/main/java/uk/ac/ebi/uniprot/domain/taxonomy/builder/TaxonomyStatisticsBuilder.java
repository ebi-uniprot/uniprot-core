package uk.ac.ebi.uniprot.domain.taxonomy.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyStatistics;
import uk.ac.ebi.uniprot.domain.taxonomy.impl.TaxonomyStatisticsImpl;

public class TaxonomyStatisticsBuilder implements Builder<TaxonomyStatisticsBuilder, TaxonomyStatistics> {

    private long reviewedProteinCount;
    private long unreviewedProteinCount;
    private long proteomeCount;

    public TaxonomyStatisticsBuilder reviewedProteinCount(long reviewedProteinCount){
        this.reviewedProteinCount = reviewedProteinCount;
        return this;
    }
    public TaxonomyStatisticsBuilder unreviewedProteinCount(long unreviewedProteinCount){
        this.unreviewedProteinCount = unreviewedProteinCount;
        return this;
    }
    public TaxonomyStatisticsBuilder proteomeCount(long proteomeCount){
        this.proteomeCount = proteomeCount;
        return this;
    }

    @Override
    public TaxonomyStatistics build() {
        return new TaxonomyStatisticsImpl(reviewedProteinCount,unreviewedProteinCount,proteomeCount);
    }

    @Override
    public TaxonomyStatisticsBuilder from(TaxonomyStatistics instance) {
        this.reviewedProteinCount(instance.getReviewedProteinCount());
        this.unreviewedProteinCount(instance.getUnreviewedProteinCount());
        this.proteomeCount(instance.getProteomeCount());
        return this;
    }
}
